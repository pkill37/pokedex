package silio.pokedex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


        PokemonCard pokemon = (PokemonCard) getArguments().getSerializable("pokemon");

        List<Move> moves = new ArrayList<>();
        for (Move move : pokemon.getMoves()) {
            moves.add(new Move(move.getName(), move.getMethod(), move.getPower(), move.getAccuracy(), move.getPp(), move.getType(), move.getCategory()));
        }

        PokemonMovesAdapter adapter = new PokemonMovesAdapter(moves);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}