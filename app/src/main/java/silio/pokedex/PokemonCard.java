package silio.pokedex;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class PokemonCard implements Serializable{
    private int id;
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private String name;
    private String description;
    private Type[] types;
    private String sprite;
    private List<Move> moves;
    private Map<Integer, String> evolutions;

    public PokemonCard(int id, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed, String name, String description, Type[] types, String sprite, List<Move> moves, Map<Integer, String> evolutions) {

        this.id = id;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
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


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
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
