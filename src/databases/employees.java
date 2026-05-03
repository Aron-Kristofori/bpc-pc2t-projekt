package databases;
import people.zamestnanec;
import java.util.HashMap;

public class employees {
    private HashMap<Integer, zamestnanec> employeeDB;

    public employees(){}

    public void addEmployee(zamestnanec pointer){
        if(employeeDB.get(pointer.getID()) == null){
            employeeDB.put(pointer.getID(), pointer);
        } else {
            System.out.println("Employee with this ID already exists!");
        }
    }

    public void printEmployees(){
        employeeDB.forEach((key, employee) -> {
            System.out.println("ID: " + key + ", Name: " + employee.getName() + " " + employee.getSurname());
        });
    }

}
