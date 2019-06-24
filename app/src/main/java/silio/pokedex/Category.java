package silio.pokedex;

public enum Category {
    physical("physical"),
    special("special"),
    status("status");

    private String category;
    private int color;

    Category(String category) {
        switch (category) {
            case "physical":
                this.color = R.color.colorPhysicalMove;
                break;
            case "special":
                this.color = R.color.colorSpecialMove;
                break;
            case "status":
                this.color = R.color.colorStatusMove;
                break;
        }
    }

    public int color() {
        return this.color;
    }

    public String category() {
        return this.category;
    }
}
