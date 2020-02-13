package com.svafvel.software.production.favoriteapp.localstorage.helper;

import android.database.Cursor;


import com.svafvel.software.production.favoriteapp.localstorage.database.DatabaseContract;
import com.svafvel.software.production.favoriteapp.localstorage.filmfavorite.FilmItemsFavorite;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<FilmItemsFavorite> mapCursorToArrayList(Cursor filmCursor){

        ArrayList<FilmItemsFavorite> favorites = new ArrayList<>();

        while(filmCursor.moveToNext()){

            int id = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID));
            String bgIMG = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.BGIMG));
            String img = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.IMG));
            String title = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE));
            String rating = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.RATING));
            String type = filmCursor.getString(filmCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TYPE));
            favorites.add(new FilmItemsFavorite(id, img, bgIMG, title, rating, type));

        }

        return favorites;

    }

}
