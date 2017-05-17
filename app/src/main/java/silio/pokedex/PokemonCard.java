package silio.pokedex;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;

public class PokemonCard {
    String pokemon_name;
    int id;
    String primary_type;
    String secondary_type;
    Uri spriteURI;

    public PokemonCard() {
    }

    public PokemonCard(int id, String name, String primary_type) {
        this.id = id;
        this.pokemon_name = name;
        this.primary_type = primary_type;
    }

    public PokemonCard(int id, String name, String primary_type, Uri spriteURI) {
        this.id = id;
        this.pokemon_name = name;
        this.primary_type = primary_type;
        this.spriteURI = spriteURI;
    }

    public PokemonCard(int id, String name, String primary_type, String secondary_type) {
        this.pokemon_name = name;
        this.id = id;
        this.primary_type = primary_type;
        this.secondary_type = secondary_type;
    }

    public PokemonCard(int id, String name, String primary_type, String secondary_type, Uri spriteURI) {
        this.pokemon_name = name;
        this.id = id;
        this.primary_type = primary_type;
        this.secondary_type = secondary_type;
        this.spriteURI = spriteURI;
    }

    public void setSprite(Uri spriteURI) {
        this.spriteURI = spriteURI;
    }

    public Uri getSprite() {
        return spriteURI;
    }
}
