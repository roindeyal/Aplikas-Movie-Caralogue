package com.svafvel.software.production.moviecataloguelocalstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.navigation.SectionsPagerAdapterFavorite;


public class FavoriteActivity extends AppCompatActivity {

    public final static String EXTRA_FILM_FAVORITE = "extra film favorite";

    private FavoriteFragment _currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        SectionsPagerAdapterFavorite sectionsPagerAdapterFavorite = new SectionsPagerAdapterFavorite(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_favorite);
        viewPager.setAdapter(sectionsPagerAdapterFavorite);
        TabLayout tabs = findViewById(R.id.tabs_favorite);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setIcon(R.drawable.ic_movie_white_24dp);
        tabs.getTabAt(1).setIcon(R.drawable.ic_live_tv_white_24dp);

        getSupportActionBar().setElevation(0);

        if(getSupportActionBar() != null){

            getSupportActionBar().setTitle(getResources().getString(R.string.film_favorite));
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }



}

