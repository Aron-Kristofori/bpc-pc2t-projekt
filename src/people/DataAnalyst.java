package people;

import java.util.Map;

public class DataAnalyst extends Employee {
    private static final long serialVersionUID = 1L;

    public DataAnalyst(int ID, int YoB, String name, String surname) {
        super(ID, YoB, name, surname);
    }

    @Override
    public void performSkill(Map<Integer, Employee> allEmployees) {
        System.out.println("\nData Analyst Skill: Most Collaborators");
        if (collaborations.isEmpty()) {
            System.out.println("This analyst has no collaborators.");
            return;
        }

        int bestCollabId = -1;
        int maxMutual = -1;

        for (Integer collabId : collaborations.keySet()) {
            Employee colleague = allEmployees.get(collabId);
            if (colleague != null) {
                int mutualCount = 0;
                for (Integer mutualId : colleague.getCollaborations().keySet()) {
                    if (mutualId != this.getID() && this.collaborations.containsKey(mutualId)) {
                        mutualCount++;
                    }
                }
                if (mutualCount > maxMutual) {
                    maxMutual = mutualCount;
                    bestCollabId = collabId;
                }
            }
        }

        if (bestCollabId != -1) {
            Employee best = allEmployees.get(bestCollabId);
            System.out.println("Colleague with most mutual connections: " + best.getName() + " " + best.getSurname() + " (ID: " + bestCollabId + ") with " + maxMutual + " mutuals.");
        } else {
            System.out.println("No mutual collaborators found.");
        }
    }
}