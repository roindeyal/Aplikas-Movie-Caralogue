package com.svafvel.software.production.favoriteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.svafvel.software.production.favoriteapp.localstorage.navigation.SectionsPagerAdapterFavorite;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
