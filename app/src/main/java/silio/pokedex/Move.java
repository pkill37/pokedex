package silio.pokedex;

public class Move extends Object {
    private final String name;
    private final String description;
    private final int level;
    private final int power;
    private final int accuracy;
    private final int pp;
    private final Type type;
    private final Category category;

    public enum Category {
        STATUS(R.color.colorAccent),
        PHYSICAL(R.color.colorIcons),
        SPECIAL(R.color.colorPrimary);

        private int color;

        Category(int color) {
            this.color = color;
        }

        public int color() {
            return color;
        }
    }

    public Move(String name, String description, int level, int power, int accuracy, int pp, Type type, Category category) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.type = type;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getPp() {
        return pp;
    }

    public Type getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }
}
