package com.vipul.popularmovies.event;

import com.vipul.popularmovies.model.Movies;

/**
 * Created by HP-HP on 03-06-2016.
 */
public class MovieSelectedEvent {

    public final Movies movies;

    public MovieSelectedEvent(Movies movies) {
        this.movies = movies;
    }

    public Movies getSelectedMovie() {
        return movies;
    }

}
