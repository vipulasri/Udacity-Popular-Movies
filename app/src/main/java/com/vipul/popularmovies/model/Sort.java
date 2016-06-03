package com.vipul.popularmovies.model;

/**
 * Created by HP-HP on 27-03-2016.
 */
public enum Sort {

    POPULAR("popular"),
    TOP_RATED("top_rated"),
    FAVORITE("favorite");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    @Override public String toString() {
        return value;
    }
}
