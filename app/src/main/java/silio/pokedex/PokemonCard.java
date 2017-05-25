package silio.pokedex;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class PokemonCard {
    private int id;
    private String name;
    private String description;
    private Type[] types;
    private Uri sprite;
    private List<Move> moves;
    private Map<Integer, String> evolutions;

    public PokemonCard(int id, String name, String description, Type[] types, Uri sprite, List<Move> moves, Map<Integer, String> evolutions) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.types = types;
        this.sprite = sprite;
        this.moves = moves;
        this.evolutions = evolutions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    public Uri getSprite() {
        return sprite;
    }

    public void setSprite(Uri sprite) {
        this.sprite = sprite;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Map<Integer, String> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(Map<Integer, String> evolutions) {
        this.evolutions = evolutions;
    }
}
