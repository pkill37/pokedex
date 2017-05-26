package silio.pokedex;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView mRecyclerView;
    private PokemonCardAdapter mPokedexAdapter;
    private PokemonCardAdapter mFavoritesAdapter;
    private GridLayoutManager mLayoutManager;
    private SearchView mSearchView;
    private MenuItem mSearchItem;
    private LinearLayout filterSearch;
    private boolean filterFlag = false;
    private boolean searchFlag = false;
    private boolean favoritePage = false;

    // could be put in a sort of Utils class, that'd be nice
    private final String baseURL = "http://pokeapi.co/api/v2/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Auto generated */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /* Auto generated end*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);

            }
        });

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            mLayoutManager = new GridLayoutManager(this, 2);
        else
            mLayoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibility = (mLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) ? View.VISIBLE : View.GONE;
                if (mPokedexAdapter.getItemCount() != 0)
                    fab.setVisibility(visibility);
            }
        });

        filterSearch = (LinearLayout)findViewById(R.id.filterSearch);
        // Get Pokémons

        try {
            new RequestPokemonCardsTask().execute();
        } catch(Exception e) {
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setFloatingActionButton(final View view) {
        FloatingActionButton actionButton = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
                        .getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
    }

    // ActionBar Stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchViewItem = menu.findItem(R.id.action_search);
        mSearchItem = searchViewItem;
        final MenuItem searchFilterItem = menu.findItem(R.id.action_filter);

        if (searchViewItem != null)
            tintMenuIcon(MainActivity.this, searchViewItem, android.R.color.white);

        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setMaxWidth( Integer.MAX_VALUE );

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if ( TextUtils.isEmpty ( newText ) ) {
                    mPokedexAdapter.getFilter().filter("");
                    //Don't show keyboard on clear
                    //Not proud of how it's done
                    if(filterFlag && !searchFlag) {
                        mSearchView.post(new Runnable() {
                            @Override
                            public void run() {
                                mSearchView.clearFocus();
                            }
                        });
                        for(int index=0; index<filterSearch.getChildCount(); ++index) {
                            ToggleButton child = (ToggleButton) filterSearch.getChildAt(index);
                            child.setChecked(false);
                        }
                    }

                } else {
                    mPokedexAdapter.getFilter().filter(newText);

                }
                return false;
            }

        });
        mSearchView = searchViewAndroidActionBar;

        // search filter visibility indicators
        // filter scroller
        final HorizontalScrollView filter = (HorizontalScrollView) findViewById(R.id.filter);

        for(int index=0; index<filterSearch.getChildCount(); ++index) {

            final ToggleButton child = (ToggleButton) filterSearch.getChildAt(index);
            child.setTransformationMethod(null);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(child.isChecked()){
                        CharSequence previousQuery = searchViewAndroidActionBar.getQuery();
                        if(TextUtils.isEmpty(previousQuery))
                            searchViewAndroidActionBar.setQuery(child.getText(),false);
                        else {
                            searchViewAndroidActionBar.setQuery(
                                    previousQuery + ", " + child.getText(), false);
                        }
                        /*if(child.isChecked()){
                            //do something on enabling it
                            child.setEnabled(false);  // disable it
                        }*/
                    }
                    child.setChecked(true);
                }
            });
            // check it is enabled



        }
        //searchFilterItem.setVisible(true);

        // Search State
        MenuItemCompat.setOnActionExpandListener(mSearchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.i("DBG",Boolean.toString(filterFlag));
                if(filterFlag) {
                    searchFilterItem.setVisible(false);
                    filter.setVisibility(View.VISIBLE);
                    mSearchView.setFocusable(false);


                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                filterFlag = false;
                searchFlag = false;
                mSearchView.setFocusable(true);
                searchFilterItem.setVisible(true);
                filter.setVisibility(View.GONE);
                for(int index=0; index<filterSearch.getChildCount(); ++index) {
                    ToggleButton child = (ToggleButton) filterSearch.getChildAt(index);
                    child.setChecked(false);
                }
                return true;
            }
        });


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            searchFlag = true;
            return true;
        }

        // filter state changed
        if (id == R.id.action_filter) {
            filterFlag = true;
            mSearchItem.expandActionView();
            mSearchView.clearFocus();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Easy way to bypass Theme shenanigans
    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));

        item.setIcon(wrapDrawable);
    }

    // NavBar Stuff

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            favoritePage = false;
            getSupportActionBar().setTitle("Pokédex");
            mPokedexAdapter.setAll(getApplicationContext());
            mPokedexAdapter.getFilter().filter("");
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;


        } else if (id == R.id.nav_caught) {
            favoritePage = true;
            getSupportActionBar().setTitle("Favorite");
            mPokedexAdapter.setFav(getApplicationContext());
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        } /*else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // gets card info from @assets/pokedex.json
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("pokedex.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    // gets first 151 pokemon card info from file
    // not in separate class 'cause private references are useful
    private class RequestPokemonCardsTask extends AsyncTask<Void, Integer, List<PokemonCard>> {

        List<PokemonCard> pokemonCardList = new ArrayList<>();

        protected List<PokemonCard> doInBackground(Void... params) {

            try {
                JSONArray pokemonCardArray = new JSONArray(loadJSONFromAsset());

                for (int i=0; i< pokemonCardArray.length(); i++) {
                    JSONObject pokemonCard = (JSONObject) pokemonCardArray.get(i);

                    int id = pokemonCard.getInt("id");
                    int hp = pokemonCard.getInt("hp");
                    int attack = pokemonCard.getInt("attack");
                    int defense = pokemonCard.getInt("defense");
                    int specialAttack = pokemonCard.getInt("special_attack");
                    int specialDefense = pokemonCard.getInt("special_defense");
                    int speed = pokemonCard.getInt("speed");
                    String name = pokemonCard.getString("name");
                    String description = pokemonCard.getString("description");
                    String sprite = pokemonCard.getString("sprite");

                    JSONArray movesJson = pokemonCard.getJSONArray("moves");
                    List<Move> moves = new ArrayList<>();
                    for (int j = 0; j < movesJson.length(); j++) {
                        JSONObject moveJson = (JSONObject) movesJson.get(j);
                        moves.add(new Move(
                                moveJson.getString("name"),
                                Integer.toString(moveJson.getInt("method")),
                                Integer.valueOf(moveJson.getString("power").equals("null") ? "0" : moveJson.getString("power")),
                                Integer.valueOf(moveJson.getString("accuracy").equals("null") ? "0": moveJson.getString("accuracy")),
                                Integer.valueOf(moveJson.getString("pp").equals("null") ? "0" : moveJson.getString("pp")),
                                Type.valueOf(moveJson.getString("type")),
                                Move.Category.valueOf(moveJson.getString("category"))
                        ));
                    }

                    JSONArray evolutionsJson = pokemonCard.getJSONArray("evolutions");
                    Map<Integer, String> evolutions = new HashMap<>();
                    for (int k = 0; k < evolutionsJson.length(); k++) {
                        JSONObject evolutionJson = (JSONObject) evolutionsJson.get(k);
                        evolutions.put(
                                Integer.valueOf(evolutionJson.getString("id")),
                                evolutionJson.getString("name")
                        );
                    }

                    JSONArray typesJson = pokemonCard.getJSONArray("types");
                    Type[] types;
                    if(typesJson.length() > 1) {
                        types = new Type[]{ Type.valueOf(typesJson.getString(0)), Type.valueOf(typesJson.getString(1)) };
                    } else {
                        types = new Type[]{ Type.valueOf(typesJson.getString(0)), null };
                    }

                    pokemonCardList.add(new PokemonCard(id, hp, attack, defense, specialAttack, specialDefense, speed, name, description, types, sprite, moves, evolutions));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return pokemonCardList;
        }

        @Override
        protected void onPostExecute(List<PokemonCard> pokemonCardList) {
            super.onPostExecute(pokemonCardList);
            mPokedexAdapter = new PokemonCardAdapter(pokemonCardList);
            mRecyclerView.setAdapter(mPokedexAdapter);
        }

    }

    @Override
    protected void onRestart() {
        if(favoritePage) {
            mPokedexAdapter.setFav(getApplicationContext());
        }
        super.onRestart();
    }



}
