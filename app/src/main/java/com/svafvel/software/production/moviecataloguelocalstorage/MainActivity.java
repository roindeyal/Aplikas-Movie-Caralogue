package com.svafvel.software.production.moviecataloguelocalstorage;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.svafvel.software.production.moviecataloguelocalstorage.navigation.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_containerHome,homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.detach(homeFragment);
        fragmentTransaction.commit();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setIcon(R.drawable.ic_movie_white_24dp);
        tabs.getTabAt(1).setIcon(R.drawable.ic_live_tv_white_24dp);

        getSupportActionBar().setElevation(0);

        if(getSupportActionBar() != null){

            getSupportActionBar().setTitle("Movie Catalogue");

        }

    }


}
