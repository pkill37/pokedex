package silio.pokedex;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

public class RequestPokemonsTask extends AsyncTask<Integer, Integer, List<PokemonSpecies>> {
    protected List<PokemonSpecies> doInBackground(Integer... params) {
        List<PokemonSpecies> list = new ArrayList<>();
        PokeApi pokeApi = new PokeApiClient();

        for (Integer id : params) {
            try {
                PokemonSpecies pokemon = pokeApi.getPokemonSpecies(id);
                list.add(pokemon);
                Log.d(getClass().getName(), pokemon.toString());
            } catch (Exception e) {
                Log.d(getClass().getName(), e.toString());
            }
        }

        return list;
    }
}
