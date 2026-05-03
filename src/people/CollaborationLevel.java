package people;

public enum CollaborationLevel {
    BAD(1),
    AVERAGE(2),
    GOOD(3);

    private final int weight;

    CollaborationLevel(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public static CollaborationLevel fromInt(int value) {
        return switch (value) {
            case 1 -> BAD;
            case 3 -> GOOD;
            default -> AVERAGE;
        };
    }
}
