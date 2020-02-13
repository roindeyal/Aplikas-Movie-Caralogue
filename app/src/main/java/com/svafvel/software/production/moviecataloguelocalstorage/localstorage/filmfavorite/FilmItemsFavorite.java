package com.svafvel.software.production.moviecataloguelocalstorage.localstorage.filmfavorite;

import android.os.Parcel;
import android.os.Parcelable;



public class FilmItemsFavorite implements Parcelable {

    private int id;
    private String img;
    private String bgImg;
    private String title;
    private String rating;
    private String voteCount;
    private String popularity;
    private String age;


    public FilmItemsFavorite() {

    }

    public FilmItemsFavorite(int id, String img, String bgImg, String title, String rating, String type) {
        this.id = id;
        this.img = img;
        this.bgImg = bgImg;
        this.title = title;
        this.rating = rating;
        this.type = type;
    }

    private String language;
    private String description;
    private String releaseDate;
    private String type;

    protected FilmItemsFavorite(Parcel in) {
        id = in.readInt();
        img = in.readString();
        bgImg = in.readString();
        title = in.readString();
        rating = in.readString();
        voteCount = in.readString();
        popularity = in.readString();
        age = in.readString();
        language = in.readString();
        description = in.readString();
        type = in.readString();
        releaseDate = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(img);
        dest.writeString(bgImg);
        dest.writeString(title);
        dest.writeString(rating);
        dest.writeString(voteCount);
        dest.writeString(popularity);
        dest.writeString(age);
        dest.writeString(language);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(releaseDate);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilmItemsFavorite> CREATOR = new Creator<FilmItemsFavorite>() {
        @Override
        public FilmItemsFavorite createFromParcel(Parcel in) {
            return new FilmItemsFavorite(in);
        }

        @Override
        public FilmItemsFavorite[] newArray(int size) {
            return new FilmItemsFavorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
