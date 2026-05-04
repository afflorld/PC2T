package components;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    private final String name;
    private final String surname;
    private final Date dateOfBirth;
    private final int id;

    ArrayList<Collab> collabs = new ArrayList<>();

    public Employee(String name, String surname, Date dateOfBirth, int id) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void addCollab(Collab collab) {
        collabs.add(collab);
    }

    public ArrayList<Collab> getCollabs() {
        return collabs;
    }

    public abstract void Duty(List<Employee> Employees);
}
