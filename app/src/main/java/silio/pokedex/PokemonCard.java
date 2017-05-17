package silio.pokedex;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;

public class PokemonCard {
    private String pokemonName;
    private int id;
    private String primaryType;
    private String secondaryType;
    private Uri spriteURI;

    public PokemonCard() {
    }

    public PokemonCard(int id, String name, String primaryType) {
        this.id = id;
        this.pokemonName = name;
        this.primaryType = primaryType;
    }

    public PokemonCard(int id, String name, String primaryType, Uri spriteURI) {
        this.id = id;
        this.pokemonName = name;
        this.primaryType = primaryType;
        this.spriteURI = spriteURI;
    }

    public PokemonCard(int id, String name, String primaryType, String secondaryType) {
        this.pokemonName = name;
        this.id = id;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
    }

    public PokemonCard(int id, String name, String primaryType, String secondaryType, Uri spriteURI) {
        this.pokemonName = name;
        this.id = id;
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
        this.spriteURI = spriteURI;
    }

    public void setSpriteURI(Uri spriteURI) {
        this.spriteURI = spriteURI;
    }

    public Uri getSpriteURI() {
        return spriteURI;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public String getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }
}
