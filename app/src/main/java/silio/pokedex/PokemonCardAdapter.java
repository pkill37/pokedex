package silio.pokedex;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PokemonCardAdapter extends  RecyclerView.Adapter<PokemonCardAdapter.PokemonCardViewHolder> {

    private List<PokemonCard> pokemonCardList;
    private List<PokemonCard> originalList;

    private static final HashMap<String, Integer> typeColor;
    static
    {
        typeColor = new HashMap<>();
        typeColor.put("grass", R.color.colorGrassType);
        typeColor.put("fire", R.color.colorFireType);
        typeColor.put("water", R.color.colorWaterType);
        typeColor.put("bug", R.color.colorBugType);
        typeColor.put("normal", R.color.colorNormalType);
        typeColor.put("poison", R.color.colorPoisonType);
        typeColor.put("electric", R.color.colorElectricType);
        typeColor.put("flying", R.color.colorFlyingType);
        typeColor.put("ground", R.color.colorGroundType);
        typeColor.put("fairy", R.color.colorFairyType);
        typeColor.put("fighting", R.color.colorFightingType);
        typeColor.put("psychic", R.color.colorPsychicType);
        typeColor.put("rock", R.color.colorRockType);
        typeColor.put("ghost", R.color.colorGhostType);
        typeColor.put("steel", R.color.colorSteelType);
        typeColor.put("ice", R.color.colorIceType);
        typeColor.put("dark", R.color.colorDarkType);
        typeColor.put("dragon", R.color.colorDragonType);
    }


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
            pokemonCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    context.startActivity(new Intent(context, PokemonActivity.class));
                }
            });

        }
    }

    public PokemonCardAdapter(List<PokemonCard> pokemonCardList) {
        this.pokemonCardList = pokemonCardList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonCardViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card, parent, false);
        PokemonCardViewHolder pcvh = new PokemonCardViewHolder(v);
        return pcvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // used when contents come into view
    // card info relative to position is treated here
    @Override
    public void onBindViewHolder(PokemonCardViewHolder pokemonHolder, int position) {


        String pokemonName = pokemonCardList.get(position).getPokemonName();
        String pokemonNameCapitalized = pokemonName.substring(0,1).toUpperCase() + pokemonName.substring(1);
        pokemonHolder.pokemonName.setText(pokemonNameCapitalized);

        String primaryType = pokemonCardList.get(position).getPrimaryType();
        Log.i("TG",primaryType);
        pokemonHolder.primaryTypeColor.setBackgroundResource(typeColor.get(primaryType));

        Integer secondaryColorResource = typeColor.get(pokemonCardList.get(position).getSecondaryType());

        if(secondaryColorResource != null)
            pokemonHolder.secondaryTypeColor.setBackgroundResource(secondaryColorResource);
        else
            pokemonHolder.secondaryTypeColor.setBackgroundResource(typeColor.get(primaryType));

        // load sprite to card
        Uri uri = pokemonCardList.get(position).getSpriteURI();
        Context context = pokemonHolder.sprite.getContext();
        if(pokemonName.equals("ditto"))
            Picasso.with(context).load(R.drawable.easter).into(pokemonHolder.sprite);
        else
            Picasso.with(context).load(uri)
                   .into(pokemonHolder.sprite);
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

                                if (g.getSecondaryType() != null)
                                    matchesSecondaryType = g.getSecondaryType().toLowerCase().contains(searchSubstring);

                                boolean matchesSubstring =
                                        g.getPrimaryType().toLowerCase().contains(searchSubstring) ||
                                        matchesSecondaryType ||
                                        g.getPokemonName().toLowerCase().contains(searchSubstring) ||
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

}


