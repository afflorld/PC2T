package core;

import components.*;
import java.io.*;
import java.sql.Date;
import java.util.*;

public class Filer {

    public static void saveToFile(List<Employee> employees) {
        // try just insures that the PrintWriter is closed automatically
        try(PrintWriter pw = new PrintWriter("database.txt")){ 
            for(Employee e : employees){
                // we determine the subclass type of each employee
                String type = (e instanceof DataAnalytic) ? "DataAnalytic" : "SecuritySpecialist";
                // we print his subclass name dob and id separated by collums so we know which part is what
                pw.println(type + ";" + e.getName() + ";" + e.getSurname() + ";" + e.getDateOfBirth() + ";" + e.getId());

                for(Collab c : e.getCollabs()){// then after that first line we print collabs of that employee
                    pw.println("Collab," + c.getCollab().getId() + "," + c.getType());
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public static int loadFromFile(List<Employee> employees) {

        employees.clear();
        int maxId = 0;
        File file = new File("database.txt");

        // first we load employees
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // this is just to simplify the syntax

                // we skip collabs
                if(line.startsWith("Collab,")) {
                    continue;
                }

                // we setup an array of parts which are separed by ;
                String[] parts = line.split(";");

                if(parts.length < 5) { // theyre 4 
                    continue;
                }

                // and we load the employee to the list

                String type = parts[0];
                String name = parts[1];
                String surname = parts[2];
                Date dateOfBirth = Date.valueOf(parts[3]);
                int id = Integer.parseInt(parts[4]);

                Employee e = type.equals("DataAnalytic") ? new DataAnalytic(name, surname, dateOfBirth, id) : new SecuritySpecialist(name, surname, dateOfBirth, id);
                employees.add(e);

                if (id >= maxId) maxId = id + 1; // increment id and set maxId 
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        // now we load collabs
        try(Scanner scanner = new Scanner(file)){
            Employee current = null; // first we need an employee to assign thats why we make an empty var

            while (scanner.hasNextLine()) { // we go through each line
                String line = scanner.nextLine();

                if (!line.startsWith("Collab,")) { // if we find employee we have to get his id and assign the collabs to him
                    String[] p = line.split(";"); // first we isolate the parts for the employee
                    current = findId(employees,Integer.parseInt(p[4])); // we have to find the employee

                } else if (current != null) { // if we find him, we assign the collab
                    String[] p = line.split(","); // we isolate each part and put it into an array
                    int cId = Integer.parseInt(p[1]);
                    String quality = p[2];

                    Employee him = findId(employees,cId); // we find the collaborator
                    if (him != null) {
                        current.addCollab(new Collab(him, quality)); // add the collab
                    }
                }
            }
        }catch (FileNotFoundException ignored){}
        return maxId; // return the max id
    }

    public static Employee findId(List<Employee> employees, int id) { // help function to find id of employees
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
}
