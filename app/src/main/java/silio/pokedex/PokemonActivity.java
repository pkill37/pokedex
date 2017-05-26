package silio.pokedex;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PokemonActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private boolean star_checked = false;
    private String pokemonName;
    private PokemonCard pokemonCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pokemon_detail, menu);

        Set<String> caught = new HashSet<>();
        SharedPreferences settings = getSharedPreferences("pokedex", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        caught =  settings.getStringSet("caught", new HashSet<String>());

        if(caught.contains(pokemonName))
            star_checked = true;

        menu.findItem(R.id.action_favorite).setIcon(star_checked?R.drawable.ic_star_24dp:R.drawable.ic_star_border_24dp);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_catch || id == R.id.action_favorite) {
            return false;
        }


        return super.onOptionsItemSelected(item);
        */

        if (id == R.id.action_favorite){
            star_checked=!star_checked;
            invalidateOptionsMenu();
            Log.i("DBG","in fav");
            Set<String> caught;



            if(star_checked){

                SharedPreferences settings = getSharedPreferences("pokedex", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();

                caught =  settings.getStringSet("caught", new HashSet<String>());
                caught.add(pokemonName);
                editor.remove("caught");
                editor.apply();
                editor.putStringSet("caught",caught);
                editor.apply();

            }
            else{
                SharedPreferences settings = getSharedPreferences("pokedex", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                caught =  settings.getStringSet("caught", new HashSet<String>());
                caught.remove(pokemonName);
                editor.remove("caught");
                editor.apply();
                editor.putStringSet("caught",caught);
                editor.apply();

            }
            return true;

        }
        return false;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                pokemonCard = (PokemonCard) bundle.getSerializable("pokemon");
                pokemonName = pokemonCard.getName();
            }

            switch (position) {
                case 0:
                    Fragment a = new PokemonDetailsFragment();
                    a.setArguments(bundle);
                    return a;
                case 1:
                    Fragment b = new PokemonMovesFragment();
                    b.setArguments(bundle);
                    return b;
                case 2:
                    Fragment c = new PokemonPlacesFragment();
                    c.setArguments(bundle);
                    return c;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DETAILS";
                case 1:
                    return "MOVES";
                case 2:
                    return "PLACES";
                default:
                    return null;
            }
        }
    }
}
