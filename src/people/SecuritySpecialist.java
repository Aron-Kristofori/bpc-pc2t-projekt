package people;

import java.util.Map;

public class SecuritySpecialist extends Employee {
    private static final long serialVersionUID = 1L;

    public SecuritySpecialist(int ID, int YoB, String name, String surname) {
        super(ID, YoB, name, surname);
    }

    @Override
    public void performSkill(Map<Integer, Employee> allEmployees) {
        System.out.println("\nSecurity Specialist Skill: Risk Evaluation");
        int count = collaborations.size();
        if (count == 0) {
            System.out.println("No collaborations to evaluate. Risk Score: 0");
            return;
        }

        double sum = 0;
        for (CollaborationLevel level : collaborations.values()) {
            sum += level.getWeight();
        }
        double avgRating = sum / count;

        double riskScore = count * (4.0 - avgRating) * 2.5;
        System.out.printf("Risk Score: %.2f (Total Collaborators: %d, Average Quality: %.2f/3.0)\n", riskScore, count, avgRating);
    }
}
