package people;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private int YoB;
    private String name;
    private String surname;

    protected Map<Integer, CollaborationLevel> collaborations;

    public Employee(int ID, int YoB, String name, String surname) {
        this.ID = ID;
        this.YoB = YoB;
        this.name = name;
        this.surname = surname;
        this.collaborations = new HashMap<>();
    }

    public int getID() { return ID; }
    public int getYoB() { return YoB; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Map<Integer, CollaborationLevel> getCollaborations() { return collaborations; }

    public void addCollaboration(int colleagueID, CollaborationLevel level) {
        collaborations.put(colleagueID, level);
    }

    public void removeCollaboration(int colleagueID) {
        collaborations.remove(colleagueID);
    }

    public abstract void performSkill(Map<Integer, Employee> allEmployees);
}
