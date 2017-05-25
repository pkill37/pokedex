package silio.pokedex;

public enum Type {
    fairy("fairy"),
    psychic("psychic"),
    poison("poison"),
    ghost("ghost"),
    dragon("dragon"),
    grass("grass"),
    bug("bug"),
    water("water"),
    ice("ice"),
    flying("flying"),
    fighting("fighting"),
    fire("fire"),
    electric("electric"),
    dark("dark"),
    steel("steel"),
    rock("rock"),
    normal("normal"),
    ground("ground");

    private String type;
    private int color;

    Type(String type) {
        this.type = type;

        switch (type) {
            case "grass":
                this.color = R.color.colorGrassType;
                break;
            case "fire":
                this.color = R.color.colorFireType;
                break;
            case "water":
                this.color = R.color.colorWaterType;
                break;
            case "bug":
                this.color = R.color.colorBugType;
                break;
            case "normal":
                this.color = R.color.colorNormalType;
                break;
            case "poison":
                this.color = R.color.colorPoisonType;
                break;
            case "electric":
                this.color = R.color.colorElectricType;
                break;
            case "flying":
                this.color = R.color.colorFlyingType;
                break;
            case "ground":
                this.color = R.color.colorGroundType;
                break;
            case "fairy":
                this.color = R.color.colorFairyType;
                break;
            case "fighting":
                this.color = R.color.colorFightingType;
                break;
            case "psychic":
                this.color = R.color.colorPsychicType;
                break;
            case "rock":
                this.color = R.color.colorRockType;
                break;
            case "ghost":
                this.color = R.color.colorGhostType;
                break;
            case "steel":
                this.color = R.color.colorSteelType;
                break;
            case "ice":
                this.color = R.color.colorIceType;
                break;
            case "dark":
                this.color = R.color.colorDarkType;
                break;
            case "dragon":
                this.color = R.color.colorDragonType;
                break;
        }
    }

    public String type() {
        return this.type;
    }

    public int color() {
        return this.color;
    }


}
