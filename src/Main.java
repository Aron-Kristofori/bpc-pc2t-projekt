import databases.CompanyDatabase;
import databases.SQLHandler;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CompanyDatabase database = new CompanyDatabase();
        database.setEmployees(SQLHandler.loadFromSQL());

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running){
            System.out.println("\n--- Company Database ---");
            System.out.println("1) Add an employee");
            System.out.println("2) Add a collaboration");
            System.out.println("3) Remove an employee");
            System.out.println("4) Search for employee by ID");
            System.out.println("5) Execute employee skill");
            System.out.println("6) Alphabetical list by groups");
            System.out.println("7) Show statistics");
            System.out.println("8) Show employee counts");
            System.out.println("9) Save employee to file");
            System.out.println("10) Load employee from file");
            System.out.println("0) Quit and Save to SQL");
            System.out.print("Select an option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch(choice){
                case 1:
                    System.out.println("Select Group: \n1. Data Analyst\n2. Security Specialist");
                    int profession = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Surname: ");
                    String surname = scanner.nextLine();
                    System.out.print("Year of birth: ");
                    int yob = scanner.nextInt(); scanner.nextLine();
                    database.addEmployee(profession, name, surname, yob);
                    break;
                case 2:
                    System.out.print("Employee ID: ");
                    int id1 = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Colleague ID: ");
                    int id2 = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Collaboration rating (1-Bad, 2-Average, 3-Good): ");
                    int rating = scanner.nextInt(); scanner.nextLine();
                    if(rating >= 1 && rating <= 3) {
                        database.addCollaboration(id1, id2, rating);
                    } else {
                        System.out.println("Invalid rating!");
                    }
                    break;
                case 3:
                    System.out.print("ID of employee to remove: ");
                    int id3 = scanner.nextInt(); scanner.nextLine();
                    database.removeEmployee(id3);
                    break;
                case 4:
                    System.out.print("ID of employee to search: ");
                    int id4 = scanner.nextInt(); scanner.nextLine();
                    database.printEmployee(id4);
                    break;
                case 5:
                    System.out.print("ID of employee to trigger skill: ");
                    int id5 = scanner.nextInt(); scanner.nextLine();
                    database.triggerSkill(id5);
                    break;
                case 6:
                    database.printAlphabetically();
                    break;
                case 7:
                    database.printStatistics();
                    break;
                case 8:
                    database.printCounts();
                    break;
                case 9:
                    System.out.print("ID of employee to save: ");
                    int id6 = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Filename: ");
                    String filenameSave = scanner.nextLine();
                    database.saveEmployeeToFile(id6, filenameSave);
                    break;
                case 10:
                    System.out.print("Filename to load: ");
                    String filenameLoad = scanner.nextLine();
                    database.loadEmployeeFromFile(filenameLoad);
                    break;
                case 0:
                    SQLHandler.saveToSQL(database.getEmployees());
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}
