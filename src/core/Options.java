package core;

import components.*;
import java.sql.Date;
import java.util.*;

public class Options {

    public static int addEmployee(List<Employee> employees, Scanner scanner, int identifier) {
        System.out.print("Enter employee type (DataAnalytic/SecuritySpecialist): ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine().trim();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        try{
            String dateOfBirthInput = scanner.nextLine().trim();
            Date dateOfBirth = Date.valueOf(dateOfBirthInput);

            Employee employee;

            if (type.equalsIgnoreCase("DataAnalytic")) {

                employee = new DataAnalytic(name, surname, dateOfBirth, identifier);

            } else if (type.equalsIgnoreCase("SecuritySpecialist")) {

                employee = new SecuritySpecialist(name, surname, dateOfBirth, identifier);
            } else {
                System.out.println("Invalid employee type.");
                return identifier;
            }

            employees.add(employee);
            System.out.println("Employee added successfully.");

            return identifier + 1;

        } catch(Exception e){
            System.out.println("Invalid date format. Employee not added.");
            return identifier;
        }
    }

    public static void addCollab(List<Employee> employees, Scanner scanner) {

        if (employees.size() < 2) {
            System.out.println("No employees available to collaborate.");
            return;
        }

        System.out.print("Enter employee ID: ");
        int eId;

        try{
            eId = Integer.parseInt(scanner.nextLine().trim());
        } catch(Exception e){
            System.out.println("Invalid ID format.");
            return;
        }

        Employee employee = findId(employees,eId);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("Enter collaborator ID: ");
        int cId;

        try{
             cId = Integer.parseInt(scanner.nextLine().trim());
        } catch(Exception e){
            System.out.println("Invalid ID format.");
            return;
        }

        Employee collaborator = findId(employees,cId);
        if (collaborator == null) {
            System.out.println("Collaborator not found.");
            return;
        }

        if(cId == eId) {
            System.out.println("Employee cannot collaborate with themselves.");
            return;
        }

        System.out.print("Enter collaboration type (good/average/poor): ");
        String type = scanner.nextLine().trim();

        Collab ecollab = new Collab(collaborator, type);
        employee.addCollab(ecollab);

        Collab ccollab = new Collab(employee, type);
        collaborator.addCollab(ccollab);
        System.out.println("Collaboration added successfully.");


    }

    public static void remove(List<Employee> employees, Scanner scanner) {

        if(employees.isEmpty()) {
            System.out.println("No employees to remove.");
            return;
        }

        System.out.print("Enter employee ID to remove: ");
        int id;

        try{
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch(Exception e){
            System.out.println("Invalid ID format.");
            return;
        }

        Employee employee = findId(employees,id);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        for (Employee e : employees) {
            e.getCollabs().removeIf(c -> c.getCollab().getId() == id);
        }

        employees.remove(employee);
        System.out.println("Employee removed successfully.");
    }

    public static void find(List<Employee> employees, Scanner scanner) {

        if(employees.isEmpty()) {
            System.out.println("No employees to find.");
            return;
        }

        System.out.print("Enter employee ID to find: ");
        int id;

        try{
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch(Exception e){
            System.out.println("Invalid ID format.");
            return;
        }

        Employee employee = findId(employees,id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        System.out.println(employee.getName() + " " + employee.getSurname());
    }

    public static void skill(List<Employee> employees, Scanner scanner) {

        if(employees.isEmpty()) {
            System.out.println("No employees to check skills.");
            return;
        }

        System.out.print("Enter employee ID: ");
        int id;

        try{
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch(Exception e){
            System.out.println("Invalid ID format.");
            return;
        }

        Employee employee = findId(employees,id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        employee.Duty(employees);
    }

    public static void printAlpha(List<Employee> employees) {

        employees.sort(Comparator.comparing(Employee::getSurname));

        System.out.println("\nDataAnalytics:");
        for (Employee employee : employees) {
            if (employee instanceof DataAnalytic) {
                System.out.println(employee.getName() + " " + employee.getSurname() + " " + employee.getDateOfBirth() + " " + employee.getId());
            }
        }

        System.out.println("\nSecuritySpecialists:");
        for (Employee employee : employees) {
            if (employee instanceof SecuritySpecialist) {
                System.out.println(employee.getName() + " " + employee.getSurname() + " " + employee.getDateOfBirth() + " " + employee.getId());
            }
        }
    }

    public static void printStats(List<Employee> employees) {

        if(employees.isEmpty()) {
            System.out.println("No employees to calculate stats.");
            return;
        }

        int goodCount = 0;
        int averageCount = 0;
        int poorCount = 0;

        String result;

        int max = 0;
        int id = -1;

        for( Employee employee : employees){

            for (Collab collab : employee.getCollabs()) {
                switch (collab.getType()) {
                    case "good": goodCount++; break;
                    case "average": averageCount++; break;
                    case "poor": poorCount++; break;
                }
            }

            if(employee.getCollabs().size() > max) {
                max = employee.getCollabs().size();
                id = employee.getId();
            }
        }

        result = resultStats(goodCount, averageCount, poorCount);

        System.out.println("Good collabs: " + goodCount + ", Average collabs: " + averageCount + ", Poor collabs: " + poorCount + ". Overall result: " + result);
        System.out.println("Employee with most collabs has " + max + " collabs. And his id is : " + id);
    }

    public static void printCounts(List<Employee> employees) {

        if(employees.isEmpty()) {
            System.out.println("No employees to count.");
            return;
        }

        int AnalyticsCount = 0;
        int SecurityCount = 0;

        for (Employee employee : employees) {
            if (employee instanceof DataAnalytic) {
                AnalyticsCount++;
            } else if (employee instanceof SecuritySpecialist) {
                SecurityCount++;
            }
        }

        System.out.println("Data Analytics: " + AnalyticsCount + ", Security Specialists: " + SecurityCount);
    }

    public static Employee findId(List<Employee> employees, int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public static String resultStats(int goodCount, int averageCount, int poorCount) {
        if(goodCount > averageCount && goodCount > poorCount) {
            return "Good";
        } else if(averageCount > goodCount && averageCount > poorCount) {
            return "Average";
        } else if(poorCount > goodCount && poorCount > averageCount) {
            return "Poor";
        } else {
            return "No clear majority.";
        }
    }

}
