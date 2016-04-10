package com.vipul.popularmovies.dto;

import com.vipul.popularmovies.model.Movies;

import java.util.List;

/**
 * Created by HP-HP on 27-03-2016.
 */
public class MoviesResponse {

    private Integer page;
    private Integer totalPages;
    private Integer totalResults;
    private List<Movies> results;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }
}
