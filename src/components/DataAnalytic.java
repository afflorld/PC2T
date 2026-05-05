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

        for(Collab c : this.collabs) { // looping through all my collaps

            Employee collaborator = null;

            for (Employee e : employees) { // looping through all employees in the company and checking if there are any that i collab with
                if (e.getId() == c.getCollab().getId()) {
                    collaborator = e;
                    break;
                }
            }

            // we go through all of his collabs and counting them if theyre in my collab's list too
            if (collaborator != null) { // if we find a collaborator
                int count = 0;
                for (Collab c_c : collaborator.collabs) { // collaborator's collabs
                    for (Collab check_c : this.collabs) { // check if their in my collabs
                        if (check_c.getCollab().getId() == c_c.getCollab().getId()) {
                            count++;
                        }
                    }
                }

                if (count > max) { // if they're are just set the id and new max value
                    max = count;
                    id = collaborator.getId();
                }
            }
        }

        System.out.println("This employee " + id + " has the most same collaborators " + max + " as you");
    } 
}
