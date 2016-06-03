package com.vipul.popularmovies.event;

import com.vipul.popularmovies.model.MovieVideos;

/**
 * Created by HP-HP on 03-06-2016.
 */
public class MovieVideosEvent {

    public final MovieVideos movieVideos;

    public MovieVideosEvent(MovieVideos movieVideos) {
        this.movieVideos = movieVideos;
    }

    public MovieVideos getMovieVideos() {
        return movieVideos;
    }

}
