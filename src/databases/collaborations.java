package databases;

import java.util.HashMap;

public class collaborations {
    private HashMap<Integer, HashMap<Integer, Integer>> collabs;

    public collaborations(){
        this.collabs = new HashMap<>();
    }

    public void addCollab(int firstEmployee, int secondEmployee, int rating){
        if(rating < 1 || rating > 5){
            System.out.println("Invalid rating!");
        }
        if(isCreated(firstEmployee)){
            collabs.get(firstEmployee).put(secondEmployee, rating);
        } else {
            HashMap<Integer, Integer> nested = new HashMap<>();
            collabs.put(firstEmployee, nested);
            collabs.get(firstEmployee).put(secondEmployee, rating);
        }
        if(isCreated(secondEmployee)){
            collabs.get(secondEmployee).put(firstEmployee, rating);
        } else {
            HashMap<Integer, Integer> nested2 = new HashMap<>();
            collabs.put(secondEmployee, nested2);
            collabs.get(secondEmployee).put(firstEmployee, rating);
        }
    }

    private boolean isCreated(int ID){
        if(collabs.get(ID) != null){
            return true;
        } else {
            return false;
        }
    }

    public void removeCollabs(int ID){
        if(isCreated(ID)){
            collabs.get(ID).forEach((key, rating) -> {
                collabs.get(key).remove(ID);
            });
            collabs.remove(ID);
        } else {
            System.out.println("Employee has no collaborations or doesn't exist!");
        }
    }

    public void printCollabs(){
        System.out.println("--- Collabs ---");
        collabs.forEach((key, map) -> {
            System.out.println("--- ID: " + key + " ---");
            map.forEach((ID, rating) -> {
                System.out.println("-> " + ID + " | " + rating);
            });
        });
        System.out.println("---------------");
    }

    public void printCollabs(int ID){
        final int[] ratingSum = new int[1];
        final int[] countCollabs = new int[1];
        System.out.println("--- Collabs ---");
        HashMap<Integer, Integer> map = collabs.get(ID);
            System.out.println("--- ID: " + ID + " ---");
            map.forEach((colleague, rating) -> {
                System.out.println("-> " + colleague + " | " + rating);
                ratingSum[0] += rating;
                countCollabs[0]++;
            });
        System.out.println("---------------");
        System.out.println("No. of collabs: " + countCollabs[0] + "| Avg. rating: " + ((float)ratingSum[0]/countCollabs[0]));
    }
}
