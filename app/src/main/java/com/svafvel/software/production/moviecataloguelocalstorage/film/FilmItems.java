package com.svafvel.software.production.moviecataloguelocalstorage.film;

import android.os.Parcel;
import android.os.Parcelable;


public class FilmItems implements Parcelable {

    private int id;
    private String img;
    private String bgImg;
    private String title;
    private float rating;
    private int voteCount;

    private double popularity;
    private String age;
    private String language;
    private String description;
    private String releaseDate;
    private String type;

    public FilmItems(Parcel in) {
        id = in.readInt();
        img = in.readString();
        bgImg = in.readString();
        title = in.readString();
        rating = in.readFloat();
        voteCount = in.readInt();
        popularity = in.readDouble();
        age = in.readString();
        language = in.readString();
        description = in.readString();
        releaseDate = in.readString();
        type = in.readString();
    }

    public static final Creator<FilmItems> CREATOR = new Creator<FilmItems>() {
        @Override
        public FilmItems createFromParcel(Parcel in) {
            return new FilmItems(in);
        }

        @Override
        public FilmItems[] newArray(int size) {
            return new FilmItems[size];
        }
    };

    public FilmItems() {

    }

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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(img);
        parcel.writeString(bgImg);
        parcel.writeString(title);
        parcel.writeFloat(rating);
        parcel.writeInt(voteCount);
        parcel.writeDouble(popularity);
        parcel.writeString(age);
        parcel.writeString(language);
        parcel.writeString(description);
        parcel.writeString(releaseDate);
        parcel.writeString(type);
    }
}
