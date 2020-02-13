package com.svafvel.software.production.favoriteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svafvel.software.production.favoriteapp.localstorage.adapter.FavoriteFilmAdapter;
import com.svafvel.software.production.favoriteapp.localstorage.filmfavorite.FilmItemsFavorite;


import java.text.DecimalFormat;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra_film";
    public static final String EXTRA_POSITION = "extra_position";

    ImageView imgFilm, bgFilm;

    TextView txtTitle, txtDescription, txtReleaseDate, txtRating, txtAge, txtLanguage,
            txtPopularity, txtVoteAverage, txtVoteCount;

    private RatingBar ratingBar;
    private FloatingActionButton fabFavorite;
    private FilmItemsFavorite filmItemsFavorite;
    private FavoriteFilmAdapter favoriteAdapter;

    public boolean IS_FAVORITE;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favoriteAdapter = new FavoriteFilmAdapter();

        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filmItemsFavorite = getIntent().getParcelableExtra(EXTRA_FILM);
        prepare();
        addItemsFavorite();


        fabFavorite = findViewById(R.id.fab_favorite);



    }

    private void prepare(){

        imgFilm = findViewById(R.id.photo_film_detail);
        bgFilm = findViewById(R.id.bg_film_detail);
        txtTitle = findViewById(R.id.title_film_detail);
        txtRating = findViewById(R.id.rating_film_detail);
        ratingBar = findViewById(R.id.ratingbar_film_detail);
        txtDescription = findViewById(R.id.description_film_detail);
        txtReleaseDate = findViewById(R.id.releasedate_film_detail);
        txtAge = findViewById(R.id.age_film_detail);
        txtLanguage = findViewById(R.id.language_film_detail);
        txtPopularity = findViewById(R.id.popularity_film_detail);
        txtVoteAverage = findViewById(R.id.voteaverage_film_detail);
        txtVoteCount = findViewById(R.id.votecount_film_detail);


    }


    private void addItemsFavorite(){

        getSupportActionBar().setTitle(filmItemsFavorite.getTitle());


        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185"+filmItemsFavorite.getImg())
                .into(imgFilm);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342"+filmItemsFavorite.getBgImg())
                .into(bgFilm);
        txtTitle.setText(filmItemsFavorite.getTitle());

        double n = Double.parseDouble(filmItemsFavorite.getRating()) / 2;

        txtRating.setText(String.valueOf(new DecimalFormat("##.#").format(n)));
        ratingBar.setRating(Float.parseFloat(String.valueOf(n)));

        txtDescription.setText(filmItemsFavorite.getDescription());
        txtReleaseDate.setText(filmItemsFavorite.getReleaseDate());

        if(Boolean.parseBoolean(filmItemsFavorite.getAge()) == true){



            txtAge.setText(getResources().getString(R.string.adult));

        }else {

            txtAge.setText(getResources().getString(R.string.all_ages));

        }

        txtLanguage.setText(filmItemsFavorite.getLanguage());
        txtPopularity.setText(String.valueOf(filmItemsFavorite.getPopularity()));
        txtVoteAverage.setText(String.valueOf(new DecimalFormat("##.#").format(n)));
        txtVoteCount.setText(String.valueOf(filmItemsFavorite.getVoteCount()));

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
