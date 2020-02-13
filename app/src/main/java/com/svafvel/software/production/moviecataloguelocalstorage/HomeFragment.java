package com.svafvel.software.production.moviecataloguelocalstorage;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmAdapter;
import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmItems;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.filmfavorite.FilmItemsFavorite;
import com.svafvel.software.production.moviecataloguelocalstorage.model.MainViewModel;
import com.svafvel.software.production.moviecataloguelocalstorage.model.SearchViewModel;
import com.svafvel.software.production.moviecataloguelocalstorage.setting.SettingActivity;

import java.util.ArrayList;
import java.util.Locale;

import static com.svafvel.software.production.moviecataloguelocalstorage.FavoriteActivity.EXTRA_FILM_FAVORITE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ProgressBar progressBar;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private FilmAdapter adapter;
    private MainViewModel mainViewModel;
    private ArrayList<FilmItems> filmItems = new ArrayList<>();


    private int index;



    public static HomeFragment newInstance(int index){
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        setHasOptionsMenu(true);
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new FilmAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new FilmAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(FilmItems filmItems) {

                showDetailSelectedFilm(filmItems);
            }
        });


        index = 1;
        if (getArguments() != null){
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }


        prepareFilm();


        return rootView;

    }



    private void prepareFilm(){

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory()).get(MainViewModel.class);

        if(index == 1){

            mainViewModel.setFilm("movie", getActivity());
            showLoading(true);

        }else {

            mainViewModel.setFilm("tv", getActivity());
            showLoading(true);

        }


        mainViewModel.getFilms().observe(getViewLifecycleOwner(), new Observer<ArrayList<FilmItems>>() {
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

        Intent moveDetailFilm = new Intent(this.getActivity(), DetailActivity.class);
        moveDetailFilm.putExtra(DetailActivity.EXTRA_FILM, filmItems);
        startActivity(moveDetailFilm);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.setting){

            Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
            startActivity(settingIntent);

        }else if(item.getItemId() == R.id.action_favorite){

            Intent favoriteIntent = new Intent(getActivity(), FavoriteActivity.class);
            startActivity(favoriteIntent);

        }else if(item.getItemId() == R.id.search){

            Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
            searchIntent.putExtra(SearchActivity.EXTRA_INDEX, index);
            startActivity(searchIntent);

        }

        return super.onOptionsItemSelected(item);
    }



}
