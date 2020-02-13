package com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfavoritefilm";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE_MOVIE = String.format("CREATE TABLE %s " +
            " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "%s TEXT NOT NULL, " +
            "%s TEXT NOT NULL, " +
            "%s TEXT NOT NULL, " +
            "%s TEXT NOT NULL, " +
            "%s DATE NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.NoteColumns._ID,
            DatabaseContract.NoteColumns.IMG,
            DatabaseContract.NoteColumns.BGIMG,
            DatabaseContract.NoteColumns.TITLE,
            DatabaseContract.NoteColumns.RATING,
            DatabaseContract.NoteColumns.TYPE
    );

    private static final String SQL_CREATE_TABLE_FAVORITE_TV = String.format("CREATE TABLE %s " +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s DATE NOT NULL)",
            DatabaseContract.TABLE_TV,
            DatabaseContract.NoteColumns._ID,
            DatabaseContract.NoteColumns.IMG,
            DatabaseContract.NoteColumns.BGIMG,
            DatabaseContract.NoteColumns.TITLE,
            DatabaseContract.NoteColumns.RATING,
            DatabaseContract.NoteColumns.TYPE
    );

    public DatabaseHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITE_TV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TV);
        onCreate(sqLiteDatabase);

    }
}
