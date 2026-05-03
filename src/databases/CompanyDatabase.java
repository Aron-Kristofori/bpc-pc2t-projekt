package databases;

import people.CollaborationLevel;
import people.DataAnalyst;
import people.Employee;
import people.SecuritySpecialist;

import java.io.*;
import java.util.*;

public class CompanyDatabase {
    private Map<Integer, Employee> employeeDB;
    private int nextID = 1;

    public CompanyDatabase() {
        this.employeeDB = new HashMap<>();
    }

    public Map<Integer, Employee> getEmployees() {
        return employeeDB;
    }

    public void setEmployees(Map<Integer, Employee> db) {
        this.employeeDB = db;
        int maxId = 0;
        for (Integer id : db.keySet()) {
            if (id > maxId) maxId = id;
        }
        this.nextID = maxId + 1;
    }

    public void addEmployee(int type, String name, String surname, int YoB) {
        Employee emp = (type == 1)
                ? new DataAnalyst(nextID, YoB, name, surname)
                : new SecuritySpecialist(nextID, YoB, name, surname);

        employeeDB.put(nextID, emp);
        System.out.println("Added successfully! Assigned ID: " + nextID);
        nextID++;
    }

    public void addCollaboration(int id1, int id2, int rating) {
        if (!employeeDB.containsKey(id1) || !employeeDB.containsKey(id2)) {
            System.out.println("One or both IDs do not exist.");
            return;
        }
        if (id1 == id2) {
            System.out.println("Cannot collaborate with self.");
            return;
        }

        CollaborationLevel level = CollaborationLevel.fromInt(rating);
        employeeDB.get(id1).addCollaboration(id2, level);
        employeeDB.get(id2).addCollaboration(id1, level);
        System.out.println("Collaboration added successfully as: " + level.name());
    }

    public void removeEmployee(int id) {
        if (employeeDB.remove(id) != null) {
            for (Employee emp : employeeDB.values()) {
                emp.removeCollaboration(id);
            }
            System.out.println("Employee and all their collaborations removed.");
        } else {
            System.out.println("Employee does not exist.");
        }
    }

    public void printEmployee(int id) {
        Employee emp = employeeDB.get(id);
        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }
        System.out.println("ID: " + emp.getID() + " | Name: " + emp.getName() + " " + emp.getSurname() + " | YoB: " + emp.getYoB() + " | Group: " + emp.getClass().getSimpleName());
        System.out.println("--- Collaborations ---");

        if (emp.getCollaborations().isEmpty()) {
            System.out.println("No collaborations.");
        } else {
            for (Map.Entry<Integer, CollaborationLevel> entry : emp.getCollaborations().entrySet()) {
                System.out.println("-> Colleague ID: " + entry.getKey() + " | Level: " + entry.getValue().name());
            }
        }
    }

    public void triggerSkill(int id) {
        Employee emp = employeeDB.get(id);
        if (emp != null) emp.performSkill(employeeDB);
        else System.out.println("Employee not found.");
    }

    public void printAlphabetically() {
        List<Employee> analysts = new ArrayList<>();
        List<Employee> security = new ArrayList<>();

        for (Employee emp : employeeDB.values()) {
            if (emp instanceof DataAnalyst) analysts.add(emp);
            else security.add(emp);
        }

        Comparator<Employee> bySurname = Comparator.comparing(Employee::getSurname);
        analysts.sort(bySurname);
        security.sort(bySurname);

        System.out.println("--- Data Analysts ---");
        analysts.forEach(e -> System.out.println(e.getSurname() + " " + e.getName() + " (ID: " + e.getID() + ")"));
        System.out.println("\n--- Security Specialists ---");
        security.forEach(e -> System.out.println(e.getSurname() + " " + e.getName() + " (ID: " + e.getID() + ")"));
    }

    public void printStatistics() {
        if (employeeDB.isEmpty()) {
            System.out.println("No data.");
            return;
        }

        int bad = 0, avg = 0, good = 0;
        int maxConnections = -1;
        Employee mostConnected = null;

        for (Employee emp : employeeDB.values()) {
            int connections = emp.getCollaborations().size();
            if (connections > maxConnections) {
                maxConnections = connections;
                mostConnected = emp;
            }
            for (CollaborationLevel level : emp.getCollaborations().values()) {
                if (level == CollaborationLevel.BAD) bad++;
                else if (level == CollaborationLevel.AVERAGE) avg++;
                else good++;
            }
        }

        String quality = "Average";
        if (bad > avg && bad > good) quality = "Bad";
        else if (good > avg && good > bad) quality = "Good";

        System.out.println("--- Statistics ---");
        System.out.println("Overall collaboration quality: " + quality);
        if (mostConnected != null) {
            System.out.println("Employee with most links: " + mostConnected.getName() + " " + mostConnected.getSurname() + " (" + maxConnections + " links)");
        }
    }

    public void printCounts() {
        long analysts = employeeDB.values().stream().filter(e -> e instanceof DataAnalyst).count();
        long security = employeeDB.values().stream().filter(e -> e instanceof SecuritySpecialist).count();
        System.out.println("Data Analysts: " + analysts);
        System.out.println("Security Specialists: " + security);
    }

    public void saveEmployeeToFile(int id, String filename) {
        Employee emp = employeeDB.get(id);
        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(emp);
            System.out.println("Employee saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadEmployeeFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Employee emp = (Employee) ois.readObject();
            if(employeeDB.containsKey(emp.getID())) {
                System.out.println("An employee with ID " + emp.getID() + " already exists in the database.");
            } else {
                employeeDB.put(emp.getID(), emp);
                System.out.println("Employee loaded successfully.");
                if (emp.getID() >= nextID) nextID = emp.getID() + 1;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
