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
        moves.add(new Move("Fire Blast", Integer.toString(89), 85, 70, 5, Type.valueOf("fire"), Move.Category.valueOf("special")));
        moves.add(new Move("Wing Attack", Integer.toString(16), 60, 100, 35, Type.valueOf("flying"), Move.Category.valueOf("physical")));
        moves.add(new Move("Dragon Claw", Integer.toString(32), 80, 100, 15, Type.valueOf("dragon"), Move.Category.valueOf("physical")));
        moves.add(new Move("Growl", Integer.toString(46), 0, 100, 35, Type.valueOf("normal"), Move.Category.valueOf("special")));
        moves.add(new Move("Razor Leaf", Integer.toString(8), 80, 100, 15, Type.valueOf("grass"), Move.Category.valueOf("special")));

        PokemonMovesAdapter adapter = new PokemonMovesAdapter(moves);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}