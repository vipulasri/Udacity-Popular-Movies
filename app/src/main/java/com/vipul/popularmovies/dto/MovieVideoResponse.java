package com.vipul.popularmovies.dto;

import com.vipul.popularmovies.model.MovieVideos;

import java.util.List;

/**
 * Created by HP-HP on 27-03-2016.
 */
public class MovieVideoResponse {

    private Integer id;
    private List<MovieVideos> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideos> getResults() {
        return results;
    }

    public void setResults(List<MovieVideos> results) {
        this.results = results;
    }
}
