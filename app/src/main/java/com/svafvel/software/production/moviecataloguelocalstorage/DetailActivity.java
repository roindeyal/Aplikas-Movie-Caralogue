package com.svafvel.software.production.moviecataloguelocalstorage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmItems;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.adapter.FavoriteFilmAdapter;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.FilmHelper;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.filmfavorite.FilmItemsFavorite;
import com.svafvel.software.production.moviecataloguelocalstorage.setting.SettingActivity;

import java.text.DecimalFormat;

import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.NoteColumns.BGIMG;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.NoteColumns.IMG;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.NoteColumns.RATING;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.NoteColumns.TITLE;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.NoteColumns.TYPE;
import static com.svafvel.software.production.moviecataloguelocalstorage.localstorage.database.DatabaseContract.NoteColumns._ID;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra_film";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String FAVORITE = "favorite";


    private Menu menu;
    private MenuItem menuItem;

    private ImageView poster_path;
    private TextView description, rating,releaseDate, age, language, popularity, voteAverage, voteCount;

    private RatingBar ratingBar;
    private FilmItems filmItems;
    private FloatingActionButton fabFavorite;
    private FilmHelper filmHelper;
    private FilmItemsFavorite filmItemsFavorite;
    private FavoriteFilmAdapter favoriteAdapter;

    public boolean IS_FAVORITE;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        prepare();

        filmHelper = FilmHelper.getInstance(this.getApplicationContext());
        filmHelper.open();

        favoriteAdapter = new FavoriteFilmAdapter();

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if(scrollRange + i == 0){
                    isShow = true;
                    showOption(R.id.action_fab);
                }else if(isShow){
                    isShow = false;
                    hideOption(R.id.action_fab);
                }

            }
        });

        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        Cursor IDFilm = null;
        try {

            filmItems = getIntent().getParcelableExtra(EXTRA_FILM);
            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(filmItems.getTitle());
            }

            if(filmItems.getType().equalsIgnoreCase("movie")){


                IDFilm = filmHelper.queryById("movie",String.valueOf(filmItems.getId()));

            }else {


                IDFilm = filmHelper.queryById("tv", String.valueOf(filmItems.getId()));

            }

            addItemsFilm();

        }catch (Exception e){


            filmItemsFavorite = getIntent().getParcelableExtra(EXTRA_FILM);
            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(filmItemsFavorite.getTitle());
            }
            if(filmItemsFavorite.getType().equalsIgnoreCase("movie")){


                IDFilm = filmHelper.queryById("movie",String.valueOf(filmItemsFavorite.getId()));

            }else {


                IDFilm = filmHelper.queryById("tv", String.valueOf(filmItemsFavorite.getId()));

            }
            addItemsFavorite();

        }

        fabFavorite = findViewById(R.id.fab_favorite);
        menuItem = findViewById(R.id.action_fab);
        if(IDFilm.moveToFirst()){

            if(IDFilm != null){

                fabFavorite.setImageResource(R.drawable.ic_favorite);

                IS_FAVORITE = true;

            }else {

                fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
                IS_FAVORITE = false;
            }

        }else {

            fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
            IS_FAVORITE = false;

        }

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IS_FAVORITE == false){

                    ContentValues values = new ContentValues();

                    values.put(_ID, filmItems.getId());
                    values.put(IMG, filmItems.getImg());
                    values.put(BGIMG, filmItems.getBgImg());
                    values.put(TITLE, filmItems.getTitle());
                    values.put(RATING, filmItems.getRating());
                    values.put(TYPE, filmItems.getType());

                    long result;

                    if(filmItems.getType().equalsIgnoreCase("movie")){

                        result = FilmHelper.insert("movie", values);

                    }else {

                        result = FilmHelper.insert("tv", values);

                    }

                    if(result > 0){

                        IS_FAVORITE = true;
                        fabFavorite.setImageResource(R.drawable.ic_favorite);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.you_are_a_favorite) + " " + filmItems.getTitle(), Toast.LENGTH_SHORT).show();


                    }else {

                        IS_FAVORITE = false;

                        fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed) , Toast.LENGTH_SHORT).show();


                    }


                }else {

                    long result;

                    try {

                        if(filmItems.getType().equalsIgnoreCase("movie")){

                            result = FilmHelper.deleteById("movie", String.valueOf(filmItems.getId()));

                        }else {

                            result = FilmHelper.deleteById("tv", String.valueOf(filmItems.getId()));

                        }

                        if(result > 0){


                            Toast.makeText(DetailActivity.this, getResources().getString(R.string.you_are_not_a_favorite ) + " " + filmItems.getTitle(), Toast.LENGTH_SHORT).show();
                            fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
                            Intent intentFavorite = new Intent(DetailActivity.this, FavoriteActivity.class);
                            intentFavorite.putExtra(FavoriteFragment.POSITION, position);
                            intentFavorite.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentFavorite);

                            IS_FAVORITE = false;

                        }else {


                            Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed) , Toast.LENGTH_SHORT).show();
                            IS_FAVORITE = true;

                        }

                    }catch (Exception e){

                        if(filmItemsFavorite.getType().equalsIgnoreCase("movie")){

                            result = FilmHelper.deleteById("movie", String.valueOf(filmItemsFavorite.getId()));

                        }else {

                            result = FilmHelper.deleteById("tv", String.valueOf(filmItemsFavorite.getId()));

                        }


                        if(result > 0){

                            Toast.makeText(DetailActivity.this, getResources().getString(R.string.you_are_not_a_favorite) + " " + filmItemsFavorite.getTitle(), Toast.LENGTH_SHORT).show();
                            fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
                            Intent intentFavorite = new Intent(DetailActivity.this, FavoriteActivity.class);
                            intentFavorite.putExtra(FavoriteFragment.POSITION, position);
                            intentFavorite.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentFavorite);
                            IS_FAVORITE = false;


                        }else {


                            Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed) , Toast.LENGTH_SHORT).show();
                            IS_FAVORITE = true;

                        }


                    }



                }
            }
        });


    }

    private void prepare(){

        poster_path = findViewById(R.id.poster_path);
        description = findViewById(R.id.description_film_detail);
        rating = findViewById(R.id.rating_film_detail);
        ratingBar = findViewById(R.id.ratingbar_film_detail);
        releaseDate = findViewById(R.id.releasedate_film_detail);
        age = findViewById(R.id.age_film_detail);
        language = findViewById(R.id.language_film_detail);
        popularity = findViewById(R.id.popularity_film_detail);
        voteAverage = findViewById(R.id.voteaverage_film_detail);
        voteCount = findViewById(R.id.votecount_film_detail);



    }

    private void addItemsFilm(){


            //getSupportActionBar().setTitle(filmItems.getTitle());

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w185"+filmItems.getImg())
                    .into(poster_path);


            double n = filmItems.getRating() / 2;

            rating.setText(String.valueOf(new DecimalFormat("##.#").format(n)));
            ratingBar.setRating(Float.parseFloat(String.valueOf(n)));
            description.setText(filmItems.getDescription());

        releaseDate.setText(filmItems.getReleaseDate());
        if(filmItems.getAge().equalsIgnoreCase("true")){

            age.setText(getResources().getString(R.string.adult));

        }else {

            age.setText(getResources().getString(R.string.all_ages));

        }

        language.setText(filmItems.getLanguage());
        popularity.setText(String.valueOf(filmItems.getPopularity()));
        voteAverage.setText(String.valueOf(filmItems.getRating()));
        voteCount.setText(String.valueOf(filmItems.getVoteCount()));

    }

    private void addItemsFavorite(){

        //getSupportActionBar().setTitle(filmItemsFavorite.getTitle());


        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185"+filmItemsFavorite.getImg())
                .into(poster_path);

        double n = Double.parseDouble(filmItemsFavorite.getRating()) / 2;

        rating.setText(String.valueOf(new DecimalFormat("##.#").format(n)));
        ratingBar.setRating(Float.parseFloat(String.valueOf(n)));
        description.setText(filmItemsFavorite.getDescription());

        releaseDate.setText(filmItemsFavorite.getReleaseDate());

        if(Boolean.parseBoolean(filmItemsFavorite.getAge()) == true){



            age.setText(getResources().getString(R.string.adult));

        }else {

            age.setText(getResources().getString(R.string.all_ages));

        }

        language.setText(filmItemsFavorite.getLanguage());
        popularity.setText(String.valueOf(filmItemsFavorite.getPopularity()));
        voteAverage.setText(String.valueOf(new DecimalFormat("##.#").format(n)));
        voteCount.setText(String.valueOf(filmItemsFavorite.getVoteCount()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        hideOption(R.id.action_fab);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(DetailActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_fab) {

            if(IS_FAVORITE == false){

                ContentValues values = new ContentValues();

                values.put(_ID, filmItems.getId());
                values.put(IMG, filmItems.getImg());
                values.put(BGIMG, filmItems.getBgImg());
                values.put(TITLE, filmItems.getTitle());
                values.put(RATING, filmItems.getRating());
                values.put(TYPE, filmItems.getType());

                long result;

                if(filmItems.getType().equalsIgnoreCase("movie")){

                    result = FilmHelper.insert("movie", values);

                }else {

                    result = FilmHelper.insert("tv", values);

                }

                if(result > 0){

                    IS_FAVORITE = true;
                    fabFavorite.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.you_are_a_favorite) + " " + filmItems.getTitle(), Toast.LENGTH_SHORT).show();


                }else {

                    IS_FAVORITE = false;

                    fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed) , Toast.LENGTH_SHORT).show();


                }


            }else {

                long result;

                try {

                    if(filmItems.getType().equalsIgnoreCase("movie")){

                        result = FilmHelper.deleteById("movie", String.valueOf(filmItems.getId()));

                    }else {

                        result = FilmHelper.deleteById("tv", String.valueOf(filmItems.getId()));

                    }

                    if(result > 0){


                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.you_are_not_a_favorite ) + " " + filmItems.getTitle(), Toast.LENGTH_SHORT).show();
                        fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
                        Intent intentFavorite = new Intent(DetailActivity.this, FavoriteActivity.class);
                        intentFavorite.putExtra(FavoriteFragment.POSITION, position);
                        intentFavorite.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentFavorite);

                        IS_FAVORITE = false;

                    }else {


                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed) , Toast.LENGTH_SHORT).show();
                        IS_FAVORITE = true;

                    }

                }catch (Exception e){

                    if(filmItemsFavorite.getType().equalsIgnoreCase("movie")){

                        result = FilmHelper.deleteById("movie", String.valueOf(filmItemsFavorite.getId()));

                    }else {

                        result = FilmHelper.deleteById("tv", String.valueOf(filmItemsFavorite.getId()));

                    }


                    if(result > 0){

                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.you_are_not_a_favorite) + " " + filmItemsFavorite.getTitle(), Toast.LENGTH_SHORT).show();
                        fabFavorite.setImageResource(R.drawable.ic_favorite_bolder);
                        Intent intentFavorite = new Intent(DetailActivity.this, FavoriteActivity.class);
                        intentFavorite.putExtra(FavoriteFragment.POSITION, position);
                        intentFavorite.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentFavorite);
                        IS_FAVORITE = false;


                    }else {


                        Toast.makeText(DetailActivity.this, getResources().getString(R.string.failed) , Toast.LENGTH_SHORT).show();
                        IS_FAVORITE = true;

                    }


                }



            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        boolean favorite = getIntent().getBooleanExtra(FAVORITE, false);
        if(!favorite){

            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);

        }else {

            Intent intent = new Intent(DetailActivity.this, FavoriteActivity.class);
            startActivity(intent);

        }
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        if(IS_FAVORITE){
            item.setIcon(R.drawable.ic_favorite_menu);
        }else {
            item.setIcon(R.drawable.ic_favorite_bolder_menu);
        }
        item.setVisible(true);
    }

}
