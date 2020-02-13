package com.svafvel.software.production.favoriteapp.model;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.svafvel.software.production.favoriteapp.R;
import com.svafvel.software.production.favoriteapp.localstorage.filmfavorite.FilmItemsFavorite;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FavoriteViewModel extends ViewModel {


    private static final String API_KEY = "b1ca6453a5660511fc0d8eb70aa35969";
    private MutableLiveData<ArrayList<FilmItemsFavorite>> listFilmsFavorite = new MutableLiveData<>();


    public void setFilm(final String film, int id, final Activity activity){

        // Request API

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<FilmItemsFavorite> listItemsFavorite = new ArrayList<>();

        String url = "http://api.themoviedb.org/3/" + film + "/" + id + "?api_key=" + API_KEY + "&language=" +
                activity.getResources().getString(R.string.localization);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    if(film.equalsIgnoreCase("movie")){


                            FilmItemsFavorite filmItemsFavorite = new FilmItemsFavorite();

                            filmItemsFavorite.setId(responseObject.getInt("id"));
                            filmItemsFavorite.setImg(responseObject.getString("poster_path"));
                            filmItemsFavorite.setBgImg(responseObject.getString("backdrop_path"));
                            filmItemsFavorite.setTitle(responseObject.getString("title"));
                            filmItemsFavorite.setRating(responseObject.getString("vote_average"));
                            filmItemsFavorite.setPopularity(responseObject.getString("popularity"));
                            filmItemsFavorite.setVoteCount(responseObject.getString("vote_count"));
                            filmItemsFavorite.setAge(responseObject.getString("adult"));


                            filmItemsFavorite.setLanguage(responseObject.getString("original_language"));
                            filmItemsFavorite.setDescription(responseObject.getString("overview"));
                            filmItemsFavorite.setReleaseDate(responseObject.getString("release_date"));
                            filmItemsFavorite.setType("movie");

                            listItemsFavorite.add(filmItemsFavorite);



                    }else if(film.equalsIgnoreCase("tv")){

                            FilmItemsFavorite filmItemsFavorite = new FilmItemsFavorite();

                            filmItemsFavorite.setId(responseObject.getInt("id"));
                            filmItemsFavorite.setImg(responseObject.getString("poster_path"));
                            filmItemsFavorite.setBgImg(responseObject.getString("backdrop_path"));
                            filmItemsFavorite.setTitle(responseObject.getString("name"));
                            filmItemsFavorite.setRating(responseObject.getString("vote_average"));
                            filmItemsFavorite.setPopularity(responseObject.getString("popularity"));
                            filmItemsFavorite.setVoteCount(responseObject.getString("vote_count"));
                            filmItemsFavorite.setAge(activity.getResources().getString(R.string.all_ages));


                            filmItemsFavorite.setLanguage(responseObject.getString("original_language"));
                            filmItemsFavorite.setDescription(responseObject.getString("overview"));
                            filmItemsFavorite.setReleaseDate(responseObject.getString("first_air_date"));
                            filmItemsFavorite.setType("tv");

                            listItemsFavorite.add(filmItemsFavorite);

                    }




                    listFilmsFavorite.postValue(listItemsFavorite);

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

    public LiveData<ArrayList<FilmItemsFavorite>> getFilmsFavorite(){

        return listFilmsFavorite;

    }

}
