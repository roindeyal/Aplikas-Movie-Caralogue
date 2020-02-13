package com.svafvel.software.production.moviecataloguelocalstorage;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmAdapter;
import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmItems;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.adapter.FavoriteFilmAdapter;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.FilmHelper;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.filmfavorite.FilmItemsFavorite;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.helper.MappingHelper;
import com.svafvel.software.production.moviecataloguelocalstorage.model.FavoriteViewModel;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String EXTRA_STATE = "extra_state";
    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String POSITION = "position";

    private RecyclerView recyclerViewFavorite;
    private FavoriteFilmAdapter adapterFavorite;
    private ProgressBar progressBarFavorite;
    private FilmHelper filmHelper;
    private ArrayList<FilmItemsFavorite> filmItemsFavorite;
    private FavoriteViewModel favoriteViewModel;

    public static FavoriteFragment newInstance(int index){
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public FavoriteFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        int index = 1;
        if (getArguments() != null){
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        progressBarFavorite = rootView.findViewById(R.id.progressBar_Favorite);

        recyclerViewFavorite = rootView.findViewById(R.id.recyclerView_Favorite);
        recyclerViewFavorite.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerViewFavorite.setHasFixedSize(true);
        adapterFavorite = new FavoriteFilmAdapter(this.getActivity(), index, rootView, this.getContext());
        recyclerViewFavorite.setAdapter(adapterFavorite);

        filmHelper = FilmHelper.getInstance(getContext());
        filmHelper.open();


        try {

            int position = getActivity().getIntent().getIntExtra(POSITION, 0);

            adapterFavorite.addItem(filmItemsFavorite);
            adapterFavorite.notifyDataSetChanged();
            adapterFavorite.removeItem(position);

        }catch (Exception e){

            e.getMessage();

        }



        if(index == 1){


            Cursor dataCursor = filmHelper.queryAll("movie");
            filmItemsFavorite = MappingHelper.mapCursorToArrayList(dataCursor);

        }else {


            Cursor dataCursor = filmHelper.queryAll("tv");
            filmItemsFavorite = MappingHelper.mapCursorToArrayList(dataCursor);

        }

        adapterFavorite.addItem(filmItemsFavorite);
        adapterFavorite.notifyDataSetChanged();

        return rootView;


    }


    @Override
    public void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapterFavorite.getListFilms());

    }





}

