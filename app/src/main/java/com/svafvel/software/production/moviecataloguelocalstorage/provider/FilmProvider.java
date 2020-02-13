package com.svafvel.software.production.moviecataloguelocalstorage.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.FilmHelper;

import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.AUTHORITY_MOVIE;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.AUTHORITY_TV;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.NoteColumns.CONTENT_URI_MOVIE;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.TABLE_MOVIE;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.TABLE_TV;

public class FilmProvider extends ContentProvider {

    private static final int FILM_MOVIE = 1;
    private static final int FILM_TV = 2;
    private static final int FILM_ID = 3;
    private FilmHelper filmHelper;

    private static final UriMatcher sUriMathcer = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMathcer.addURI(AUTHORITY_MOVIE, TABLE_MOVIE, FILM_MOVIE);
        sUriMathcer.addURI(AUTHORITY_MOVIE, TABLE_MOVIE + "/#", FILM_ID);


        sUriMathcer.addURI(AUTHORITY_MOVIE, TABLE_TV, FILM_TV);
        sUriMathcer.addURI(AUTHORITY_MOVIE, TABLE_TV + "/#", FILM_TV);
    }

    public FilmProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int deleted;
        switch (sUriMathcer.match(uri)){

            case FILM_ID:
                deleted = filmHelper.deleteById("movie",uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
        return deleted;

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long added;
        switch (sUriMathcer.match(uri)){

            case FILM_MOVIE:
                added = filmHelper.insert("movie",values);
                break;

            default:
                added = 0;
                break;

        }

        getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
        return Uri.parse(CONTENT_URI_MOVIE + "/" + added);

    }

    @Override
    public boolean onCreate() {
        filmHelper = FilmHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (sUriMathcer.match(uri)){

            case FILM_MOVIE:
                cursor = filmHelper.queryAll("movie");
                break;

            case FILM_TV :
                cursor = filmHelper.queryAll("tv");
                break;

            case FILM_ID:
                cursor = filmHelper.queryById("movie", uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;

        }

        return cursor;

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int updated = 0;
        return updated;

    }
}
