package components;

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
