package silio.pokedex;

public enum Type {
    NORMAL(R.color.colorAccent),
    FIGHTING(R.color.colorPrimary);

    private int color;

    Type(int color) {
        this.color = color;
    }

    public int color() {
        return color;
    }
}
