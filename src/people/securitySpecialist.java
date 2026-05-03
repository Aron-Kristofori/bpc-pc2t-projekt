package people;

public class securitySpecialist extends zamestnanec {
    private static int count = 0;

    public securitySpecialist(int YoB, String name, String surname){
        super(YoB, name, surname);
        count++;
    }

    public static int getCount() {return count;}

}
