package silio.pokedex;

public enum Type {
    FAIRY(R.color.colorFairyType),
    PSYCHIC(R.color.colorPsychicType),
    POISON(R.color.colorPoisonType),
    GHOST(R.color.colorGhostType),
    DRAGON(R.color.colorDragonType),
    GRASS(R.color.colorGrassType),
    BUG(R.color.colorBugType),
    WATER(R.color.colorWaterType),
    ICE(R.color.colorIceType),
    FLYING(R.color.colorFlyingType),
    FIGHTING(R.color.colorFightingType),
    FIRE(R.color.colorFireType),
    ELECTRIC(R.color.colorElectricType),
    DARK(R.color.colorDarkType),
    STEEL(R.color.colorSteelType),
    ROCK(R.color.colorRockType),
    NORMAL(R.color.colorNormalType),
    GROUND(R.color.colorGroundType);

    private int color;

    Type(int color) {
        this.color = color;
    }

    public int color() {
        return this.color;
    }
}
