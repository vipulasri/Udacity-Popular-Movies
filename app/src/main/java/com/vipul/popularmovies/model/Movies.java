package com.vipul.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-HP on 27-03-2016.
 */
public class Movies implements Parcelable {

    public static final String TAG_MOVIES = "movies";

    private int id;
    private boolean adult;
    private String poster_path;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private String popularity;
    private boolean video;
    private String vote_average;
    private int vote_count;
    private boolean favorite = false;
    
    public Movies( int id, 
             boolean adult, 
             String poster_path, 
             String overview, 
             String release_date, 
             List<Integer> genre_ids,
             String original_title,
             String original_language, 
             String title, 
             String backdrop_path, 
             String popularity, 
             boolean video, 
             String vote_average, 
             int vote_count){

        this.id = id;
        this.adult = adult;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public boolean getIsVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeList(genre_ids);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeString(popularity);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(vote_average);
        dest.writeInt(vote_count);
        dest.writeByte((byte) (favorite ? 1 : 0));

    }

    //creator
    public static final Creator CREATOR = new Creator() {

        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }

    };

    public Movies(Parcel in) {
        id = in.readInt();
        adult = in.readByte()!=0;
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();

        genre_ids = new ArrayList<Integer>();
        in.readList(genre_ids, null);

        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readString();
        video = in.readByte()!=0;
        vote_average = in.readString();
        vote_count = in.readInt();
        favorite = in.readByte()!=0;
    }
}
