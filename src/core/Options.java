package core;

import components.*;
import java.sql.Date;
import java.util.*;

public class Options {

    public static int addEmployee(List<Employee> employees, Scanner scanner, int identifier) {
        System.out.print("Enter employee type (DataAnalytic/SecuritySpecialist): "); // We set basic information of the employee
        String type = scanner.nextLine().trim();

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine().trim();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        try{ // sql type of date if not handled correctly it'll break the whole program if we type something else or in different format
            String dateOfBirthInput = scanner.nextLine().trim();
            Date dateOfBirth = Date.valueOf(dateOfBirthInput);

            Employee employee;

            if (type.equalsIgnoreCase("DataAnalytic")) { // check if its datanalytic or security specialist

                employee = new DataAnalytic(name, surname, dateOfBirth, identifier);

            } else if (type.equalsIgnoreCase("SecuritySpecialist")) {

                employee = new SecuritySpecialist(name, surname, dateOfBirth, identifier);
            } else {
                System.out.println("Invalid employee type.");
                return identifier;
            }

            employees.add(employee); // adding him into the list of employees in the firm
            System.out.println("Employee added successfully.");

            return identifier + 1; // increment id for next employee

        } catch(Exception e){
            System.out.println("Invalid date format. Employee not added.");
            return identifier;
        }
    }

    public static void addCollab(List<Employee> employees, Scanner scanner) {

        if (employees.size() < 2) { // if theres only 1 or the list is empty theres noone to collab with and nobody can collab with himself
            System.out.println("No employees available to collaborate.");
            return;
        }

        System.out.print("Enter employee ID: ");
        int eId;

        try{ // we have to handle that the input will be number
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

        Employee collaborator = findId(employees,cId); // we use our help function to get the employee to collab with
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
        employee.addCollab(ecollab); // adding him to the collab list of that employee

        Collab ccollab = new Collab(employee, type);
        collaborator.addCollab(ccollab); // same for the other side
        System.out.println("Collaboration added successfully.");


    }

    public static void remove(List<Employee> employees, Scanner scanner) {

        if(employees.isEmpty()) { // we cannot remove an employee if the list is empty
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

        for (Employee e : employees) { // we have to remove him from all employees collab's lists
            e.getCollabs().removeIf(c -> c.getCollab().getId() == id); // so we go through each collab list and we remove him if the id checks
        }

        employees.remove(employee);
        System.out.println("Employee removed successfully.");
    }

    public static void find(List<Employee> employees, Scanner scanner) {

        if(employees.isEmpty()) { // if the list is empty we cannot find anyone
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

        if(employees.isEmpty()) { // if the list is empty we cannot check skills
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

        employee.Duty(employees); // if the employee is DataAnalytic he'll do his duty and if he is SecuritySpecialist he'll do his, its polymorphism
    }

    public static void printAlpha(List<Employee> employees) {

        employees.sort(Comparator.comparing(Employee::getSurname)); // first we sort the whole employees list by surname

        System.out.println("\nDataAnalytics:");
        for (Employee employee : employees) { // then we go through the list
            if (employee instanceof DataAnalytic) { // and print out only dataAnalytics
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

        if(employees.isEmpty()) { // we cannot calculate stats of an empty list
            System.out.println("No employees to calculate stats.");
            return;
        }

        int goodCount = 0;
        int averageCount = 0;
        int poorCount = 0;

        String result;

        int max = 0;
        int id = -1;

        for( Employee employee : employees){ // we loop through all employees and their collabs and count their type of collabs
            for (Collab collab : employee.getCollabs()) {
                switch (collab.getType()) {
                    case "good": goodCount++; break;
                    case "average": averageCount++; break;
                    case "poor": poorCount++; break;
                }
            }

            if(employee.getCollabs().size() > max) { // we mark the employee with most collabs
                max = employee.getCollabs().size();
                id = employee.getId();
            }
        }

        result = resultStats(goodCount, averageCount, poorCount); // we call the help functioni to determinte the majority

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

        for (Employee employee : employees) { // to print the actual count of all employees in groups we just go through and add based of their type of job
            if (employee instanceof DataAnalytic) {
                AnalyticsCount++;
            } else if (employee instanceof SecuritySpecialist) {
                SecurityCount++;
            }
        }

        System.out.println("Data Analytics: " + AnalyticsCount + ", Security Specialists: " + SecurityCount);
    }

    public static Employee findId(List<Employee> employees, int id) { // help function to return the targeted employee by id
        for (Employee employee : employees) { // we go through the whole list
            if (employee.getId() == id) {
                return employee; // return the employee with the id we want
            }
        }
        return null;
    }

    // help function
    public static String resultStats(int goodCount, int averageCount, int poorCount) { // this function just compares values and chooses the highest and outputs based on that
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
