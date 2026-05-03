package databases;

import people.CollaborationLevel;
import people.DataAnalyst;
import people.Employee;
import people.SecuritySpecialist;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SQLHandler {
    private static final String URL = "jdbc:sqlite:company.db";

    public static void saveToSQL(Map<Integer, Employee> db) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS employees (id INTEGER PRIMARY KEY, type TEXT, yob INTEGER, name TEXT, surname TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS collaborations (emp_id INTEGER, collab_id INTEGER, rating INTEGER)");

            stmt.execute("DELETE FROM employees");
            stmt.execute("DELETE FROM collaborations");

            PreparedStatement pstmtEmp = conn.prepareStatement("INSERT INTO employees VALUES (?, ?, ?, ?, ?)");
            PreparedStatement pstmtCol = conn.prepareStatement("INSERT INTO collaborations VALUES (?, ?, ?)");

            for (Employee emp : db.values()) {
                pstmtEmp.setInt(1, emp.getID());
                pstmtEmp.setString(2, emp instanceof DataAnalyst ? "DA" : "SS");
                pstmtEmp.setInt(3, emp.getYoB());
                pstmtEmp.setString(4, emp.getName());
                pstmtEmp.setString(5, emp.getSurname());
                pstmtEmp.executeUpdate();

                for (Map.Entry<Integer, CollaborationLevel> collab : emp.getCollaborations().entrySet()) {
                    pstmtCol.setInt(1, emp.getID());
                    pstmtCol.setInt(2, collab.getKey());
                    pstmtCol.setInt(3, collab.getValue().getWeight());
                    pstmtCol.executeUpdate();
                }
            }
            System.out.println("Database successfully backed up to SQL.");
        } catch (SQLException e) {
            System.out.println("SQL Save Error (Database running in memory): " + e.getMessage());
        }
    }

    public static Map<Integer, Employee> loadFromSQL() {
        Map<Integer, Employee> db = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();

            ResultSet rsEmp = stmt.executeQuery("SELECT * FROM employees");
            while (rsEmp.next()) {
                int id = rsEmp.getInt("id");
                String type = rsEmp.getString("type");
                int yob = rsEmp.getInt("yob");
                String name = rsEmp.getString("name");
                String surname = rsEmp.getString("surname");

                if ("DA".equals(type)) {
                    db.put(id, new DataAnalyst(id, yob, name, surname));
                } else {
                    db.put(id, new SecuritySpecialist(id, yob, name, surname));
                }
            }

            ResultSet rsCol = stmt.executeQuery("SELECT * FROM collaborations");
            while (rsCol.next()) {
                int empId = rsCol.getInt("emp_id");
                int collabId = rsCol.getInt("collab_id");
                int rating = rsCol.getInt("rating");
                if (db.containsKey(empId)) {
                    db.get(empId).addCollaboration(collabId, CollaborationLevel.fromInt(rating));
                }
            }
            System.out.println("Successfully loaded data from SQL database.");
        } catch (SQLException e) {
            System.out.println("No existing SQL database found or driver missing. Starting with an empty in-memory database.");
        }
        return db;
    }
}
