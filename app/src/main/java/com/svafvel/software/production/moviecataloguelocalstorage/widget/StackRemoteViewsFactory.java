package com.svafvel.software.production.moviecataloguelocalstorage.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.bumptech.glide.Glide;
import com.svafvel.software.production.moviecataloguelocalstorage.R;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.FilmHelper;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.filmfavorite.FilmItemsFavorite;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.helper.MappingHelper;

import java.util.ArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private FilmHelper filmHelper;
    private ArrayList<FilmItemsFavorite> movieItemsFavorites;
    private ArrayList<FilmItemsFavorite> tvItemsFavorites;

    StackRemoteViewsFactory(Context applicationContext) {

        context = applicationContext;

    }

    @Override
    public void onCreate() {

        filmHelper = FilmHelper.getInstance(context);
        filmHelper.open();
    }

    @Override
    public void onDataSetChanged() {

        Cursor dataMovie = filmHelper.queryAll("movie");
        Cursor dataTv = filmHelper.queryAll("tv");

        movieItemsFavorites = MappingHelper.mapCursorToArrayList(dataMovie);
        tvItemsFavorites = MappingHelper.mapCursorToArrayList(dataTv);



    }

    @Override
    public void onDestroy() {

        filmHelper.close();

    }

    @Override
    public int getCount() {
        return tvItemsFavorites.size() + movieItemsFavorites.size();
    }



    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        try {
            
            Bitmap bitmap;
            try {

                bitmap = Glide.with(context)
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w185"+ tvItemsFavorites.get(i).getImg())
                        .submit()
                        .get();

                Log.e(getClass().getSimpleName(), "Tv  " + i);

            }catch (Exception e){

                bitmap = Glide.with(context)
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w185"+ movieItemsFavorites.get(i - tvItemsFavorites.size()).getImg())
                        .submit()
                        .get();

                Log.e(getClass().getSimpleName(), "Movie  " + i);

            }

            rv.setImageViewBitmap(R.id.imageView, bitmap);

        }catch (Exception e){

            e.printStackTrace();

        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;

    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
