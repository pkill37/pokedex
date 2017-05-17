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
        moves.add(new Move("Fire Blast", "Powerful aksjdkajsdkjahskjdh", 16, 85, 70, 5, Type.FIGHTING, Move.Category.SPECIAL));
        moves.add(new Move("Fire Blast 2", "SUPER SADASPowerful aksjdkajsdkjahskjdh", 36, 85, 100, 5, Type.FIGHTING, Move.Category.SPECIAL));
        moves.add(new Move("Fire Blast 3", "MEAGK JKKJHJHPowerful aksjdkajsdkjahskjdh", 40, 100, 95, 5, Type.FIGHTING, Move.Category.SPECIAL));
        moves.add(new Move("Fire Blast 4", "MEAGK JKKJHJHPowerful aksjdkajsdkjahskjdh", 70, 60, 100, 5, Type.FIGHTING, Move.Category.SPECIAL));
        moves.add(new Move("Fire Blast 5", "MEAGK JKKJHJHPowerful aksjdkajsdkjahskjdh", 76, 85, 80, 5, Type.FIGHTING, Move.Category.SPECIAL));

        PokemonMovesAdapter adapter = new PokemonMovesAdapter(moves);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}