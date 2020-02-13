package com.svafvel.software.production.favoriteapp.localstorage.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    // Authority yang digunakan
    public static final String AUTHORITY_MOVIE = "com.svafvel.software.production.moviecataloguelocalstorage";
    public static final String AUTHORITY_TV = "com.svafvel.software.production.moviecataloguelocalstorage";
    private static final String SCHEME = "content";

    static String TABLE_MOVIE = "movie";
    static String TABLE_TV = "tv";

    public static final class NoteColumns implements BaseColumns {

        public static String IMG = "img";
        public static String BGIMG = "bg_img";
        public static String TITLE = "title";
        public static String RATING = "rating";
        public static String TYPE = "type";


        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_MOVIE)
                .appendPath(TABLE_MOVIE)
                .build();


        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_TV)
                .build();

    }

}
