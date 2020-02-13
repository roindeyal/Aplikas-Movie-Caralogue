package com.svafvel.software.production.moviecataloguelocalstorage.film;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.svafvel.software.production.moviecataloguelocalstorage.R;
import com.svafvel.software.production.moviecataloguelocalstorage.film.FilmItems;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private ArrayList<FilmItems> mData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;



    public FilmAdapter() {

    }

    public FilmAdapter(ArrayList<FilmItems> data){
        this.mData = data;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<FilmItems> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_film, parent, false);
        return new FilmViewHolder(mView);

    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {

        ImageView filmImg;
        TextView filmTitle, filmRating;
        String rating;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            filmImg = itemView.findViewById(R.id.film_img);
            filmTitle = itemView.findViewById(R.id.film_title);
            filmRating = itemView.findViewById(R.id.film_rating);
            rating = itemView.getResources().getString(R.string.rating);
        }

        void bind(FilmItems filmItems){
            filmTitle.setText(filmItems.getTitle());

            double n = filmItems.getRating() / 2;

            filmRating.setText(rating + " " + String.valueOf(new DecimalFormat("##.#").format(n)));
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185"+filmItems.getImg())
                    .into(filmImg);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(mData.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(FilmItems film);
    }
}
