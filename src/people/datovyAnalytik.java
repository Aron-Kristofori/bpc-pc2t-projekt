package people;

public class datovyAnalytik extends zamestnanec{
    private static int count = 0;

    public datovyAnalytik(int YoB, String name, String surname){
        super(YoB, name, surname);
        count++;
    }

    public static int getCount() {return count;}

}
