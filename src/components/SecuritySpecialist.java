package components;

import java.util.*;
import java.sql.Date;

public class SecuritySpecialist extends Employee {
    public SecuritySpecialist(String name, String surname, Date dateOfBirth, int id) {
        super(name, surname, dateOfBirth, id);
    }

    @Override
    public void Duty(List<Employee> employees) {

        if(this.collabs.isEmpty()){
            System.out.println("No collaborators");
            return;
        }

        int good = 0;
        int average = 0;
        int poor = 0;

        for (Collab c : this.collabs) {
            switch (c.getType()) {
                case "good" -> good++;
                case "average" -> average++;
                case "poor" -> poor++;
            }
        }

        int averageScore = (good + average*3 + poor*10) / this.collabs.size();
        int RiskScore = this.collabs.size() * averageScore;

        System.out.println("The risk score of this employee is: " + RiskScore);
    } 
}
