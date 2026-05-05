package components;

// This is just an help class for collabs which keeps track of the employees which are collaborated with an employee
public class Collab {
    private final Employee employee;
    private final String type;

    public Collab(Employee employee, String type) {
        this.employee = employee;
        this.type = type;
    }

    public Employee getCollab() {
        return employee;
    }

    public String getType(){
        return type;
    }

}
