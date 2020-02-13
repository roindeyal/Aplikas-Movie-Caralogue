package com.svafvel.software.production.moviecataloguelocalstorage.setting.rest;

import com.svafvel.software.production.moviecataloguelocalstorage.reminder.movie.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/discover/movie")
    Call<Movie> getReleaseToday(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("primary_release_date.gte") String todayDate1,
            @Query("primary_release_date.lte") String todayDate2
    );


}
