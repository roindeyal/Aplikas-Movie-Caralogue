package com.svafvel.software.production.moviecataloguelocalstorage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmAdapter;
import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmItems;
import com.svafvel.software.production.moviecataloguelocalstorage.model.SearchViewModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private FilmAdapter adapter;

    public static String EXTRA_INDEX = "extra_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getSupportActionBar() != null){

            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        progressBar = findViewById(R.id.progressBarSearch);

        recyclerView = findViewById(R.id.recyclerViewSearch);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new FilmAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new FilmAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(FilmItems film) {
                showDetailSelectedFilm(film);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    prepareFilm(query);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    private void prepareFilm(String query){

        searchViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory()).get(SearchViewModel.class);

        int index = getIntent().getIntExtra(EXTRA_INDEX, 0);

        if(index == 1){

            searchViewModel.setSearchFilm("movie", this, query);
            showLoading(true);

        }else {

            searchViewModel.setSearchFilm("tv", this, query);
            showLoading(true);

        }

        searchViewModel.getSearchFilm().observe(this, new Observer<ArrayList<FilmItems>>() {
            @Override
            public void onChanged(ArrayList<FilmItems> filmItems) {
                if(filmItems != null){
                    adapter.setData(filmItems);
                    showLoading(false);
                }
            }
        });


    }


    private void showLoading(Boolean state){
        if(state){

            progressBar.setVisibility(View.VISIBLE);

        }else {

            progressBar.setVisibility(View.GONE);

        }
    }



    private void showDetailSelectedFilm(FilmItems filmItems){

        Intent moveDetailFilm = new Intent(SearchActivity.this, DetailActivity.class);
        moveDetailFilm.putExtra(DetailActivity.EXTRA_FILM, filmItems);
        startActivity(moveDetailFilm);

    }
}
