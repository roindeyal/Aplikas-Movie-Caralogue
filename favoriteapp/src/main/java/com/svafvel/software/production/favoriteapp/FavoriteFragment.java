package com.svafvel.software.production.favoriteapp;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.svafvel.software.production.favoriteapp.localstorage.adapter.FavoriteFilmAdapter;
import com.svafvel.software.production.favoriteapp.localstorage.database.DatabaseContract;
import com.svafvel.software.production.favoriteapp.localstorage.filmfavorite.FilmItemsFavorite;
import com.svafvel.software.production.favoriteapp.localstorage.helper.MappingHelper;
import com.svafvel.software.production.favoriteapp.model.FavoriteViewModel;

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

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getContext());


        if(index == 1){


            getContext().getContentResolver().registerContentObserver(DatabaseContract
                    .NoteColumns.CONTENT_URI_MOVIE, true, myObserver);

        }else {

            getContext().getContentResolver().registerContentObserver(DatabaseContract
                    .NoteColumns.CONTENT_URI_TV, true, myObserver);

        }


        progressBarFavorite = rootView.findViewById(R.id.progressBar_Favorite);

        recyclerViewFavorite = rootView.findViewById(R.id.recyclerView_Favorite);
        recyclerViewFavorite.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerViewFavorite.setHasFixedSize(true);
        adapterFavorite = new FavoriteFilmAdapter(this.getActivity(), index, rootView, this.getContext());
        recyclerViewFavorite.setAdapter(adapterFavorite);



        try {

            int position = getActivity().getIntent().getIntExtra(POSITION, 0);

            adapterFavorite.addItem(filmItemsFavorite);
            adapterFavorite.notifyDataSetChanged();
            adapterFavorite.removeItem(position);

        }catch (Exception e){

            e.getMessage();

        }



        if(index == 1){


            Cursor dataCursor = getContext().getContentResolver().query(DatabaseContract.
                    NoteColumns.CONTENT_URI_MOVIE, null, null, null, null);
            filmItemsFavorite = MappingHelper.mapCursorToArrayList(dataCursor);

        }else {


            Cursor dataCursor = getContext().getContentResolver().query(DatabaseContract.
                    NoteColumns.CONTENT_URI_TV, null, null, null, null);
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



    public static class DataObserver extends ContentObserver{

        final Context context;

        public DataObserver(Handler handler, Context context){

            super(handler);
            this.context = context;

        }

    }


}

