package com.vipul.popularmovies.dto;

import com.vipul.popularmovies.model.Movies;

import java.util.List;

/**
 * Created by HP-HP on 27-03-2016.
 */
public class MoviesResponse {

    private Integer page;
    private List<Movies> results;
    private Integer total_pages;
    private Integer total_results;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return total_pages;
    }

    public void setTotalPages(Integer totalPages) {
        this.total_pages = totalPages;
    }

    public Integer getTotalResults() {
        return total_results;
    }

    public void setTotalResults(Integer totalResults) {
        this.total_results = totalResults;
    }

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }
}
