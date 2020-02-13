package com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class FilmHelper {

    private static DatabaseHelper databaseHelper;
    private static FilmHelper INSTANCE;

    private static SQLiteDatabase database;

    private FilmHelper(Context context){

        databaseHelper = new DatabaseHelper(context);

    }

    public static FilmHelper getInstance(Context context){

        if(INSTANCE == null){

            synchronized (SQLiteOpenHelper.class){

                if(INSTANCE == null){

                    INSTANCE = new FilmHelper(context);

                }

            }

        }

        return INSTANCE;

    }

    public void open() throws SQLException{

        database = databaseHelper.getWritableDatabase();

    }

    public void close(){

        databaseHelper.close();

        if(database.isOpen()){

            database.close();

        }

    }

    public Cursor queryAll(String TABLE){

        return database.query(
                TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );

    }

    public Cursor queryById(String TABLE, String id){

        return database.query(
                TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );

    }

    public static long insert(String TABLE, ContentValues values){

        return database.insert(TABLE, null, values);

    }

    public static int deleteById(String TABLE, String id){

        return database.delete(TABLE, _ID + " = ?", new String[]{id});

    }

}
