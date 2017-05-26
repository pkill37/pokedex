package silio.pokedex;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Map;

public class PokemonDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        Log.i("HELP","epa tou na oncreateview details");
        PokemonCard pokemon = (PokemonCard) getArguments().getSerializable("pokemon");
        Log.i("HELP",pokemon.getName());

        int hp = pokemon.getHp();
        ProgressBar barHp = (ProgressBar) rootView.findViewById(R.id.barHp);
        barHp.setProgress(statToPercentage(hp));
        TextView valueHp = (TextView) rootView.findViewById(R.id.valueHp);
        valueHp.setText(Integer.toString(hp));

        int attack = pokemon.getAttack();
        ProgressBar barAttack = (ProgressBar) rootView.findViewById(R.id.barAttack);
        barAttack.setProgress(statToPercentage(attack));
        TextView valueAttack = (TextView) rootView.findViewById(R.id.valueAttack);
        valueAttack.setText(Integer.toString(attack));

        int defense = pokemon.getDefense();
        ProgressBar barDefense = (ProgressBar) rootView.findViewById(R.id.barDefense);
        barDefense.setProgress(statToPercentage(defense));
        TextView valueDefense = (TextView) rootView.findViewById(R.id.valueDefense);
        valueDefense.setText(Integer.toString(defense));

        int specialAttack = pokemon.getSpecialAttack();
        ProgressBar barSpecialAttack = (ProgressBar) rootView.findViewById(R.id.barSpecialAttack);
        barSpecialAttack.setProgress(statToPercentage(specialAttack));
        TextView valueSpecialAttack = (TextView) rootView.findViewById(R.id.valueSpecialAttack);
        valueSpecialAttack.setText(Integer.toString(specialAttack));

        int specialDefense = pokemon.getSpecialDefense();
        ProgressBar barSpecialDefense = (ProgressBar) rootView.findViewById(R.id.barSpecialDefense);
        barSpecialDefense.setProgress(statToPercentage(specialDefense));
        TextView valueSpecialDefense = (TextView) rootView.findViewById(R.id.valueSpecialDefense);
        valueSpecialDefense.setText(Integer.toString(specialDefense));

        int speed = pokemon.getSpeed();
        Log.i("HELP","speed :"+Integer.toString(speed)+"//");
        ProgressBar barSpeed = (ProgressBar) rootView.findViewById(R.id.barSpeed);
        barSpeed.setProgress(statToPercentage(speed));
        TextView valueSpeed = (TextView) rootView.findViewById(R.id.valueSpeed);
        valueSpeed.setText(Integer.toString(speed));


        ImageView ivSprite = (ImageView) rootView.findViewById(R.id.pokemon_sprite);
        Uri uriSprite = Uri.parse(pokemon.getSprite());
        Picasso.with(getContext()).load(uriSprite).into(ivSprite);

        TextView textName = (TextView) rootView.findViewById(R.id.pokemon_name);
        textName.setText(pokemon.getName());

        TextView textType1 = (TextView) rootView.findViewById(R.id.pokemon_type1);
        textType1.setBackgroundResource(pokemon.getTypes()[0].color());
        textType1.setText(pokemon.getTypes()[0].type());

        if (pokemon.getTypes()[1] != null) {
            TextView textType2 = (TextView) rootView.findViewById(R.id.pokemon_type2);
            textType2.setVisibility(View.VISIBLE);
            textType2.setBackgroundResource(pokemon.getTypes()[1].color());
            textType2.setText(pokemon.getTypes()[1].type());
        }

        TextView textDescription = (TextView) rootView.findViewById(R.id.pokemon_description);
        textDescription.setText(pokemon.getDescription());
        int[][] ids = new int[][]{
                { R.id.pokemon_evolution1_name, R.id.pokemon_evolution1 },
                { R.id.pokemon_evolution2_name, R.id.pokemon_evolution2 },
                { R.id.pokemon_evolution3_name, R.id.pokemon_evolution3 }
        };

        Map<Integer, String> evolutions = pokemon.getEvolutions();

        int i = 0;
        for (Integer key : evolutions.keySet()) {
            TextView textEvolution = (TextView) rootView.findViewById(ids[i][0]);
            textEvolution.setVisibility(View.VISIBLE);
            textEvolution.setText(evolutions.get(key));
            ImageView ivEvolution = (ImageView) rootView.findViewById(ids[i][1]);
            ivEvolution.setVisibility(View.VISIBLE);
            Uri uriEvolution = Uri.parse("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + Integer.toString(key) + ".png");
            Picasso.with(getContext()).load(uriEvolution).fit().into(ivEvolution);
            i++;
        }

        return rootView;
    }

    public static int statToPercentage(int stat) {
        return (100 * stat) / 255;
    }
}