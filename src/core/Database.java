package core;

import components.*;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private static final String URL = "jdbc:sqlite:database.db";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void saveToSQL(List<Employee> employees) {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS employees (type TEXT, name TEXT, surname TEXT, dateOfBirth TEXT, id INTEGER PRIMARY KEY)");
            stmt.execute("CREATE TABLE IF NOT EXISTS collabs (e_id INTEGER, c_id INTEGER, quality TEXT)");

            stmt.execute("DELETE FROM collabs");
            stmt.execute("DELETE FROM employees");

            String eSql = "INSERT INTO employees VALUES(?, ?, ?, ?, ?)";
            String cSql = "INSERT INTO collabs VALUES(?, ?, ?)";

            try (PreparedStatement pstmtE = connection.prepareStatement(eSql);
                 PreparedStatement pstmtC = connection.prepareStatement(cSql)) {

                for (Employee e : employees) {
                    pstmtE.setString(1, (e instanceof DataAnalytic ? "DataAnalytic" : "SecuritySpecialist"));
                    pstmtE.setString(2, e.getName());
                    pstmtE.setString(3, e.getSurname());
                    pstmtE.setString(4, e.getDateOfBirth().toString());
                    pstmtE.setInt(5, e.getId());
                    pstmtE.executeUpdate();

                    for (Collab c : e.getCollabs()) {
                        pstmtC.setInt(1, e.getId());
                        pstmtC.setInt(2, c.getCollab().getId());
                        pstmtC.setString(3, c.getType());
                        pstmtC.executeUpdate();
                    }
                }
            }
            System.out.println("Saved...");
        } catch (SQLException e) {
            System.out.println("Couldn't save it");
        }
    }

    public static int loadFromSQL(List<Employee> employees) {
        employees.clear();
        int maxId = 0;

        Map<Integer, Employee> lookup = new HashMap<>();

        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {

            ResultSet rsE = stmt.executeQuery("SELECT * FROM employees");
            while (rsE.next()) {
                String type = rsE.getString("type");
                String name = rsE.getString("name");
                String surname = rsE.getString("surname");
                Date dateOfBirth = Date.valueOf(rsE.getString("dateOfBirth"));
                int id = rsE.getInt("id");

                Employee e = type.equals("DataAnalytic")
                        ? new DataAnalytic(name, surname, dateOfBirth, id)
                        : new SecuritySpecialist(name, surname, dateOfBirth, id);

                employees.add(e);
                lookup.put(id, e);

                if (id >= maxId) maxId = id + 1;
            }

            ResultSet rsC = stmt.executeQuery("SELECT * FROM collabs");
            while (rsC.next()) {
                int eId = rsC.getInt("e_id");
                int cId = rsC.getInt("c_id");
                String quality = rsC.getString("quality");

                Employee employee = lookup.get(eId);
                Employee collaborator = lookup.get(cId);

                if (employee != null && collaborator != null) {
                    employee.addCollab(new Collab(collaborator, quality));
                }
            }
        } catch (SQLException e) {
            System.out.println("Couldn't find the file");
        }
        return maxId;
    }
}
