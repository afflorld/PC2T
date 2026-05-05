package core;

import components.*;
import java.io.*;
import java.sql.Date;
import java.util.*;

public class Filer {

    public static void saveToFile(List<Employee> employees) {
        try(PrintWriter pw = new PrintWriter("database.txt")){
            for(Employee e : employees){
                String type = (e instanceof DataAnalytic) ? "DataAnalytic" : "SecuritySpecialist";

                pw.println(type + ";" + e.getName() + ";" + e.getSurname() + ";" + e.getDateOfBirth() + ";" + e.getId());

                for(Collab c : e.getCollabs()){
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

        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if(line.startsWith("Collab,")) {
                    continue;
                }

                String[] parts = line.split(";");

                if(parts.length < 5) {
                    continue;
                }

                String type = parts[0];
                String name = parts[1];
                String surname = parts[2];
                Date dateOfBirth = Date.valueOf(parts[3]);
                int id = Integer.parseInt(parts[4]);

                Employee e = type.equals("DataAnalytic") ? new DataAnalytic(name, surname, dateOfBirth, id) : new SecuritySpecialist(name, surname, dateOfBirth, id);
                employees.add(e);
                if (id >= maxId) maxId = id + 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        try(Scanner scanner = new Scanner(new File("database.txt"))){
            Employee current = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (!line.startsWith("Collab,")) {
                    String[] p = line.split(";");
                    current = findId(employees,Integer.parseInt(p[4]));

                } else if (current != null) {

                    String[] p = line.split(",");
                    int cId = Integer.parseInt(p[1]);
                    String quality = p[2];

                    Employee him = findId(employees,cId);
                    if (him != null) {
                        current.addCollab(new Collab(him, quality));
                    }
                }
            }
        }catch (FileNotFoundException ignored){}
        return maxId;
    }

    public static Employee findId(List<Employee> employees, int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
}
