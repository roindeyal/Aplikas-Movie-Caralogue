package com.svafvel.software.production.moviecataloguelocalstorage.localstorage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.svafvel.software.production.moviecataloguelocalstorage.DetailActivity;
import com.svafvel.software.production.moviecataloguelocalstorage.R;
import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmItems;
import com.svafvel.software.production.moviecataloguelocalstorage.localstorage.filmfavorite.FilmItemsFavorite;
import com.svafvel.software.production.moviecataloguelocalstorage.model.FavoriteViewModel;
import com.svafvel.software.production.moviecataloguelocalstorage.model.MainViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FavoriteFilmAdapter extends RecyclerView.Adapter<FavoriteFilmAdapter.FavoriteFilmViewHolder> {

    private ArrayList<FilmItemsFavorite> listFilms = new ArrayList<>();
    private Activity activity;
    private int index;
    private FavoriteViewModel favoriteViewModel;
    private FavoriteFilmAdapter favoriteFilmAdapter;
    private ArrayList<FilmItemsFavorite> listFilmDetail = new ArrayList<>();
    private View rootView;
    private Context context;

    public FavoriteFilmAdapter(Activity activity, int index, View rootView, Context context){

        this.activity = activity;
        this.index = index;
        this.rootView = rootView;
        this.context = context;
    }

    public FavoriteFilmAdapter() {

    }

    public ArrayList<FilmItemsFavorite> getListFilms(){

        return listFilms;

    }

    public void setListFilms(ArrayList<FilmItemsFavorite> filmItemsFavorite){

        if(listFilmDetail.size() > 0){

            this.listFilmDetail.clear();

        }

        this.listFilmDetail.addAll(filmItemsFavorite);
        notifyDataSetChanged();

    }

    public void addItem(ArrayList<FilmItemsFavorite> film) {

        this.listFilms.addAll(film);
        notifyItemInserted(listFilms.size());

    }
    public void updateItem(int position, FilmItemsFavorite film) {

        this.listFilms.set(position, film);
        notifyItemChanged(position, film);

    }
    public void removeItem(int position) {

        this.listFilms.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listFilms.size());

    }

    @NonNull
    @Override
    public FavoriteFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_favorite, parent,false);
        return new FavoriteFilmViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteFilmViewHolder holder, final int position) {

        holder.filmTitle.setText(listFilms.get(position).getTitle());

        double n = Double.parseDouble(listFilms.get(position).getRating()) / 2;

        holder.filmRating.setText(holder.rating + " " + String.valueOf(new DecimalFormat("##.#").format(n)));
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185"+ listFilms.get(position).getImg())
                .into(holder.filmImg);

        if(listFilms != null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    prepare(index, holder, position);


                }
            });

        }

    }


    private void prepare(final int index, final FavoriteFilmViewHolder holder, final int position){

        favoriteViewModel = new ViewModelProvider((ViewModelStoreOwner) context, new ViewModelProvider
                .NewInstanceFactory()).get(FavoriteViewModel.class);


        if(index == 1){

            favoriteViewModel.setFilm("movie", listFilms.get(position).getId(), activity);
            showLoading(true, holder);

        }else {

            favoriteViewModel.setFilm("tv", listFilms.get(position).getId(), activity);
            showLoading(true, holder);

        }

        favoriteViewModel.getFilmsFavorite().observe((LifecycleOwner) activity , new Observer<ArrayList<FilmItemsFavorite>>() {
            @Override
            public void onChanged(ArrayList<FilmItemsFavorite> filmItemsFavorites) {
                if(filmItemsFavorites != null){

                    Intent intent = new Intent(activity, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, position);
                    intent.putExtra(DetailActivity.EXTRA_FILM, filmItemsFavorites.get(0));
                    intent.putExtra(DetailActivity.FAVORITE, true);
                    activity.startActivity(intent);
                    showLoading(false, holder);
                }
            }
        });


    }

    private void showLoading(Boolean state, FavoriteFilmViewHolder holder){
        if(state){

            holder.progressBarFavorite.setVisibility(View.VISIBLE);

        }else {

            holder.progressBarFavorite.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return listFilms.size();
    }

    public class FavoriteFilmViewHolder extends RecyclerView.ViewHolder {

        ImageView filmImg;
        TextView filmTitle, filmRating;
        String rating;
        ProgressBar progressBarFavorite;

        public FavoriteFilmViewHolder(@NonNull View itemView) {
            super(itemView);

            filmImg = itemView.findViewById(R.id.film_img);
            filmTitle = itemView.findViewById(R.id.film_title);
            filmRating = itemView.findViewById(R.id.film_rating);
            rating = itemView.getResources().getString(R.string.rating);
            progressBarFavorite = rootView.findViewById(R.id.progressBar_Favorite);
        }

    }

}
