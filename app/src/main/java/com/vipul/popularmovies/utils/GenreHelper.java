package com.vipul.popularmovies.utils;

import java.util.List;

/**
 * Created by HP-HP on 25-04-2016.
 */
public class GenreHelper {

    public static String getGenreNamesList(List<Integer> ids) {

        if(ids==null || ids.size()<0)
            return "";

        String genre = "";
        String name = "";
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            switch (id) {

                case 28:
                    name = "Action"; break;
                case 12:
                    name = "Adventure"; break;
                case 16:
                    name = "Animation"; break;
                case 35:
                    name = "Comedy"; break;
                case 80:
                    name = "Crime"; break;
                case 99:
                    name = "Documentary"; break;
                case 18:
                    name = "Drama"; break;
                case 10751:
                    name = "Family"; break;
                case 14:
                    name = "Fantasy"; break;
                case 10769:
                    name = "Foreign"; break;
                case 36:
                    name = "History"; break;
                case 27:
                    name = "Horror"; break;
                case 10402:
                    name = "Music"; break;
                case 9648:
                    name = "Mystery"; break;
                case 10749:
                    name = "Romance"; break;
                case 878:
                    name = "Science Fiction"; break;
                case 10770:
                    name = "TV Movie"; break;
                case 53:
                    name = "Thriller"; break;
                case 10752:
                    name = "War"; break;
                case 37:
                    name = "Western"; break;
            }
            genre = genre + ", " + name;
        }
        return genre.substring(genre.length() > 0 ? 1 : 0);
    }

    public static String getGenreName(int id) {

        String name = "";

        switch (id) {

            case 28:
                name = "Action"; break;
            case 12:
                name = "Adventure"; break;
            case 16:
                name = "Animation"; break;
            case 35:
                name = "Comedy"; break;
            case 80:
                name = "Crime"; break;
            case 99:
                name = "Documentary"; break;
            case 18:
                name = "Drama"; break;
            case 10751:
                name = "Family"; break;
            case 14:
                name = "Fantasy"; break;
            case 10769:
                name = "Foreign"; break;
            case 36:
                name = "History"; break;
            case 27:
                name = "Horror"; break;
            case 10402:
                name = "Music"; break;
            case 9648:
                name = "Mystery"; break;
            case 10749:
                name = "Romance"; break;
            case 878:
                name = "Science Fiction"; break;
            case 10770:
                name = "TV Movie"; break;
            case 53:
                name = "Thriller"; break;
            case 10752:
                name = "War"; break;
            case 37:
                name = "Western"; break;
        }
        return name;
    }
}