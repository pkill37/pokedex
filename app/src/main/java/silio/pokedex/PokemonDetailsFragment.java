package silio.pokedex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PokemonDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        int hp = 80;
        ProgressBar barHp = (ProgressBar) rootView.findViewById(R.id.barHp);
        barHp.setProgress(statToPercentage(hp));
        TextView valueHp = (TextView) rootView.findViewById(R.id.valueHp);
        valueHp.setText(Integer.toString(hp));

        int attack = 190;
        ProgressBar barAttack = (ProgressBar) rootView.findViewById(R.id.barAttack);
        barAttack.setProgress(statToPercentage(attack));
        TextView valueAttack = (TextView) rootView.findViewById(R.id.valueAttack);
        valueAttack.setText(Integer.toString(attack));

        int defense = 120;
        ProgressBar barDefense = (ProgressBar) rootView.findViewById(R.id.barDefense);
        barDefense.setProgress(statToPercentage(defense));
        TextView valueDefense = (TextView) rootView.findViewById(R.id.valueDefense);
        valueDefense.setText(Integer.toString(defense));

        int specialAttack = 40;
        ProgressBar barSpecialAttack = (ProgressBar) rootView.findViewById(R.id.barSpecialAttack);
        barSpecialAttack.setProgress(statToPercentage(specialAttack));
        TextView valueSpecialAttack = (TextView) rootView.findViewById(R.id.valueSpecialAttack);
        valueSpecialAttack.setText(Integer.toString(specialAttack));

        int specialDefense = 150;
        ProgressBar barSpecialDefense = (ProgressBar) rootView.findViewById(R.id.barSpecialDefense);
        barSpecialDefense.setProgress(statToPercentage(specialDefense));
        TextView valueSpecialDefense = (TextView) rootView.findViewById(R.id.valueSpecialDefense);
        valueSpecialDefense.setText(Integer.toString(specialDefense));

        int speed = 240;
        ProgressBar barSpeed = (ProgressBar) rootView.findViewById(R.id.barSpeed);
        barSpeed.setProgress(statToPercentage(speed));
        TextView valueSpeed = (TextView) rootView.findViewById(R.id.valueSpeed);
        valueSpeed.setText(Integer.toString(speed));

        return rootView;
    }

    public static int statToPercentage(int stat) {
        return (100 * stat) / 255;
    }
}