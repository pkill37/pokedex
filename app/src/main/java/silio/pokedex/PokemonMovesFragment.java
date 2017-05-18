package silio.pokedex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PokemonMovesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokemon_moves, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_pokemon_moves);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<Move> moves = new ArrayList<>();
        moves.add(new Move("Fire Blast", "", 80, 85, 70, 5, Type.FIRE, Move.Category.SPECIAL));
        moves.add(new Move("Wing Attack", "", 32, 60, 100, 35, Type.FLYING, Move.Category.PHYSICAL));
        moves.add(new Move("Dragon Claw", "", 46, 80, 100, 15, Type.DRAGON, Move.Category.PHYSICAL));
        moves.add(new Move("Growl", "", 8, 0, 100, 35, Type.NORMAL, Move.Category.STATUS));
        moves.add(new Move("Razor Leaf", "", 8, 80, 100, 15, Type.GRASS, Move.Category.SPECIAL));

        PokemonMovesAdapter adapter = new PokemonMovesAdapter(moves);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}