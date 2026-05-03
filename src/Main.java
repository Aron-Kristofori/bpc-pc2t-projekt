import java.util.Scanner;

import databases.collaborations;
import databases.employees;
import people.*;


public class Main {
    public static void main(String[] args){
        boolean running = true;
        employees databaze = new employees();
        collaborations spoluprace = new collaborations();
        Scanner scanner = new Scanner(System.in);
        while(running){
            System.out.println("-------------------------------");
            System.out.println("Select an operation to do: ");
            System.out.println("1. Add an employee");
            System.out.println("2. Add a collaboration");
            System.out.println("3. Remove an employee");
            System.out.println("5. List all added employees");
            System.out.println("-------------------------------");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    System.out.println("Do you want to add an:\n1. Data Analyst\n2. Security Specialist");
                    int profession = scanner.nextInt();
                    scanner.nextLine();
                    if(profession == 1){
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Surname: ");
                        String surname = scanner.nextLine();
                        System.out.println("Year of birth: ");
                        int YoB = scanner.nextInt();
                        scanner.nextLine();
                        zamestnanec pointer = new datovyAnalytik(YoB, name, surname);
                        databaze.addEmployee(pointer);
                    } else if (profession == 2){
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Surname: ");
                        String surname = scanner.nextLine();
                        System.out.println("Year of birth: ");
                        int YoB = scanner.nextInt();
                        scanner.nextLine();
                        zamestnanec pointer = new securitySpecialist(YoB, name, surname);
                        databaze.addEmployee(pointer);
                    }
                    break;
                case 2:
                    System.out.print("Your ID: ");
                    int firstID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("ID of a colleague: ");
                    int secondID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Collaboration rating (1-5): ");
                    int rating = scanner.nextInt();
                    scanner.nextLine();
                    spoluprace.addCollab(firstID, secondID, rating);
                    break;
                case 3:
                    System.out.print("ID of employee: ");
                    int ID = scanner.nextInt();
                    scanner.nextLine();
                    spoluprace.removeCollabs(ID);
                    databaze.removeEmployee(ID);
                    break;
                case 5:
                    databaze.printEmployees();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    running = false;
            }
        }
    }
}
