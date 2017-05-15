package silio.pokedex;

public class Move extends Object {
    private final String name;
    private final String description;
    private final int power;
    private final int accuracy;
    private final int pp;
    private final Type type;
    private final Category category;

    private enum Category {
        STATUS("status"),
        PHYSICAL("physical"),
        SPECIAL("special");

        private String category;

        Category(String category) {
            this.category = category;
        }
    }

    public Move(String name, String description, int power, int accuracy, int pp, Type type, Category category) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.type = type;
        this.category = category;
    }
}
