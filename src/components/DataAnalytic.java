package components;

import java.sql.Date;
import java.util.List;

public class DataAnalytic extends Employee {

    public DataAnalytic(String name, String surname, Date dateOfBirth, int id) {
        super(name, surname, dateOfBirth, id);
    }

    @Override
    public void Duty(List<Employee> employees) {

        int max = 0;
        int id = -1;

        for(Collab c : this.collabs) {

            Employee collaborator = null;

            for (Employee e : employees) {
                if (e.getId() == c.getCollab().getId()) {
                    collaborator = e;
                    break;
                }
            }

            if (collaborator != null) {
                int count = 0;
                for (Collab c_c : collaborator.collabs) { // collaborator's collabs
                    for (Collab check_c : this.collabs) { // check if their in my collabs
                        if (check_c.getCollab().getId() == c_c.getCollab().getId()) {
                            count++;
                        }
                    }
                }

                if (count > max) {
                    max = count;
                    id = collaborator.getId();
                }
            }
        }

        System.out.println("This employee " + id + " has the most same collaborators " + max + " as you");
    } 
}
