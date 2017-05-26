package silio.pokedex;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PokemonMovesAdapter extends RecyclerView.Adapter<PokemonMovesAdapter.ViewHolder> {
    private List<Move> moves;

    public PokemonMovesAdapter(List<Move> moves) {
        this.moves = moves;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView level;
        public TextView name;
        public TextView type;
        public TextView category;
        public TextView pp;
        public TextView power;
        public TextView accuracy;

        public ViewHolder(View itemView) {
            super(itemView);
            level = (TextView) itemView.findViewById(R.id.move_level);
            name = (TextView) itemView.findViewById(R.id.move_name);
            type = (TextView) itemView.findViewById(R.id.move_type);
            category = (TextView) itemView.findViewById(R.id.move_category);
            pp = (TextView) itemView.findViewById(R.id.move_pp);
            power = (TextView) itemView.findViewById(R.id.move_power);
            accuracy = (TextView) itemView.findViewById(R.id.move_accuracy);
        }
    }

    @Override
    public PokemonMovesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_move, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Move move = moves.get(position);

        holder.level.setText(move.getMethod());

        holder.name.setText(move.getName());

        holder.type.setText(move.getType().name());
        holder.type.setBackgroundResource(move.getType().color());

        holder.category.setText(move.getCategory().name());
        holder.category.setBackgroundResource(move.getCategory().color());

        holder.pp.setText(Integer.toString(move.getPp()) + " pp");
        holder.power.setText(Integer.toString(move.getPower()) + " power");
        holder.accuracy.setText(Integer.toString(move.getAccuracy()) + " accuracy");
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }
}
