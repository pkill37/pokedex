package silio.pokedex;

import java.io.Serializable;

public class Move extends Object implements Serializable {
    private final String name;
    private final String method;
    private final int power;
    private final int accuracy;
    private final int pp;
    private final Type type;
    private final Category category;

    public Move(String name, String method, int power, int accuracy, int pp, Type type, Category category) {
        this.name = name;
        this.method = method;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.type = type;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getMethod() {
        return method;
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
