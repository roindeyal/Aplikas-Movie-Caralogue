package com.svafvel.software.production.moviecataloguelocalstorage.model;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.svafvel.software.production.moviecataloguelocalstorage.R;
import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmItems;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class MainViewModel extends ViewModel {

    private static final String API_KEY = "b1ca6453a5660511fc0d8eb70aa35969";
    private MutableLiveData<ArrayList<FilmItems>> listFilms = new MutableLiveData<>();


    public void setFilm(final String film, Activity activity){

        // Request API

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<FilmItems> listItems = new ArrayList<>();

        String url = "http://api.themoviedb.org/3/discover/" + film + "?api_key=" + API_KEY + "&language=" +
                    activity.getResources().getString(R.string.localization);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");



                    if(film.equalsIgnoreCase("movie")){

                        for (int i = 0 ; i < 10 ; i++){

                            FilmItems filmItems = new FilmItems();
                            JSONObject film = list.getJSONObject(i);

                            filmItems.setId(film.getInt("id"));
                            filmItems.setImg(film.getString("poster_path"));
                            filmItems.setBgImg(film.getString("backdrop_path"));
                            filmItems.setTitle(film.getString("title"));
                            filmItems.setRating(Float.parseFloat(String.valueOf(film.getDouble("vote_average"))));
                            filmItems.setPopularity(film.getDouble("popularity"));
                            filmItems.setVoteCount(film.getInt("vote_count"));
                            filmItems.setAge(film.getString("adult"));


                            filmItems.setLanguage(film.getString("original_language"));
                            filmItems.setDescription(film.getString("overview"));
                            filmItems.setReleaseDate(film.getString("release_date"));
                            filmItems.setType("movie");

                            listItems.add(filmItems);

                        }

                    }else if(film.equalsIgnoreCase("tv")){

                        for (int i = 0 ; i < 10 ; i++){

                            JSONObject film = list.getJSONObject(i);

                            FilmItems filmItems = new FilmItems();

                            filmItems.setId(film.getInt("id"));
                            filmItems.setImg(film.getString("poster_path"));
                            filmItems.setBgImg(film.getString("backdrop_path"));
                            filmItems.setTitle(film.getString("name"));
                            filmItems.setRating(Float.parseFloat(String.valueOf(film.getDouble("vote_average"))));
                            filmItems.setPopularity(film.getDouble("popularity"));
                            filmItems.setVoteCount(film.getInt("vote_count"));
                            filmItems.setAge("Semua Kalangan");


                            filmItems.setLanguage(film.getString("original_language"));
                            filmItems.setDescription(film.getString("overview"));
                            filmItems.setReleaseDate(film.getString("first_air_date"));
                            filmItems.setType("tv");

                            listItems.add(filmItems);

                        }

                    }


                    listFilms.postValue(listItems);

                }catch (Exception e){

                    Log.d("Exception ", e.getMessage());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.d("onFailure", error.getMessage());

            }
        });

    }

    public LiveData <ArrayList<FilmItems>> getFilms(){

        return listFilms;

    }

}
