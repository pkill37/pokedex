package silio.pokedex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PokemonCardAdapter extends  RecyclerView.Adapter<PokemonCardAdapter.PokemonCardViewHolder> {

    //base list for search (filter(""))
    private List<PokemonCard> pokemonCardList;
    //list after search
    private List<PokemonCard> originalList;
    //static pokemon list
    private List<PokemonCard> savedPokemonList;
    private boolean fav = false;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PokemonCardViewHolder extends RecyclerView.ViewHolder {
        CardView pokemonCard;
        ImageView sprite;
        TextView pokemonName;
        View primaryTypeColor;
        View secondaryTypeColor;

        PokemonCardViewHolder(final View itemView) {
            super(itemView);
            pokemonCard = (CardView) itemView.findViewById(R.id.pokemon_card);
            pokemonName = (TextView) itemView.findViewById(R.id.pokemon_name);
            sprite = (ImageView) itemView.findViewById(R.id.sprite);
            primaryTypeColor = itemView.findViewById(R.id.primary_type_color);
            secondaryTypeColor = itemView.findViewById(R.id.secondary_type_color);
        }
    }

    public PokemonCardAdapter(List<PokemonCard> pokemonCardList) {
        this.pokemonCardList = pokemonCardList;
        this.savedPokemonList = pokemonCardList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonCardViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card, parent, false);
        PokemonCardViewHolder pcvh = new PokemonCardViewHolder(v);

        return pcvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // used when contents come into view
    // card info relative to position is treated here
    @Override
    public void onBindViewHolder(final PokemonCardViewHolder pokemonHolder, final int position) {
        String name = pokemonCardList.get(position).getName();
        pokemonHolder.pokemonName.setText(name.substring(0,1).toUpperCase() + name.substring(1));

        Type[] types = pokemonCardList.get(position).getTypes();

        String primaryType = types[0].type();
        Integer primaryColorResource = Type.valueOf(primaryType).color();
        pokemonHolder.primaryTypeColor.setBackgroundResource(primaryColorResource);

        if (types[1] != null) {
            String secondaryType = types[1].type();
            Integer secondaryColorResource = Type.valueOf(secondaryType).color();
            pokemonHolder.secondaryTypeColor.setBackgroundResource(secondaryColorResource);
        } else {
            pokemonHolder.secondaryTypeColor.setBackgroundResource(primaryColorResource);
        }

        String uri = pokemonCardList.get(position).getSprite();
        Context context = pokemonHolder.sprite.getContext();

        Picasso.with(context).load(uri).into(pokemonHolder.sprite);
        pokemonHolder.pokemonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //Toast.makeText(context,.getName(),Toast.LENGTH_SHORT).show();
                //handle click
                PokemonCard clicked = pokemonCardList.get(pokemonHolder.getAdapterPosition());
                Intent i = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("pokemon", clicked);
                i.putExtras(b);
                i.setClass(context, PokemonActivity.class);
                context.startActivity(i);
            }
        });
        /*pokemonHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context,Integer.toString(pokemonHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                Log.i("HELP","onCreateviewholder");
                Log.i("HELP",Integer.toString(position));

                /*Intent pokeIntent = new Intent(context, PokemonActivity.class);
                v.getChil


            }
        });*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pokemonCardList.size();
    }

    // add search functionality
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                final FilterResults oReturn = new FilterResults();
                final List<PokemonCard> results = new ArrayList<PokemonCard>();

                if (originalList == null)
                    originalList = pokemonCardList;

                if (constraint != null) {
                    if (originalList != null & originalList.size() > 0) {
                        String[] searchSubstringArray =  constraint.toString().toLowerCase().trim().split("[\\s,;]+");

                        for (final PokemonCard g : originalList) {
                            boolean matchesFullSearch = true;

                            for(String searchSubstring : searchSubstringArray) {

                                boolean matchesSecondaryType = false;

                                if (g.getTypes()[1] != null)
                                    matchesSecondaryType = g.getTypes()[1].type().toLowerCase().contains(searchSubstring);

                                boolean matchesSubstring =
                                        g.getTypes()[0].type().toLowerCase().contains(searchSubstring) ||
                                        matchesSecondaryType ||
                                        g.getName().toLowerCase().contains(searchSubstring) ||
                                        Integer.toString(g.getId()).contains(searchSubstring);

                                matchesFullSearch = matchesFullSearch && matchesSubstring;
                            }

                            if(matchesFullSearch)
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                pokemonCardList = (ArrayList<PokemonCard>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setFav(Context context){
        SharedPreferences settings = context.getSharedPreferences("pokedex", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Set<String> caught = settings.getStringSet("caught", new HashSet<String>());

        List<PokemonCard> newList = new ArrayList<>();
        if (originalList == null)
            originalList = pokemonCardList;

        if (originalList != null & originalList.size() > 0) {
            for (final PokemonCard g : savedPokemonList) {
                for(String search: caught) {
                    if(g.getName().toLowerCase().contains(search.toLowerCase())) {
                        newList.add(g);
                        break;
                    }
                }
            }
        }
        Log.i("HELP","unique, set<String> : final size : "+ newList.size());
        pokemonCardList = newList;
        originalList = newList;
        notifyDataSetChanged();
    }

    public void setAll(Context context){

            pokemonCardList = savedPokemonList;
            originalList = savedPokemonList;
            notifyDataSetChanged();
            Log.i("DBG","settingall");

    }





}


