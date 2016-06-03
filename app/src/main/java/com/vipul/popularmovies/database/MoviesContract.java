package com.vipul.popularmovies.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {

	public static final String CONTENT_AUTHORITY = "com.vipul.popularmovies";

	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


	public static final class MoviesEntry implements BaseColumns {

		// table name
		public static final String TABLE_MOVIES = "TABLE_MOVIES";

		// columns
		public static final String ID="id";
		public static final String MOVIE_ID = "movies_id";
		public static final String MOVIE_ADULT = "movie_adult";
		public static final String MOVIE_POSTER_PATH = "movie_poster_path";
		public static final String MOVIE_OVERVIEW = "movie_overview";
		public static final String MOVIE_GENRES = "movie_genres";
		public static final String MOVIE_RELEASE_DATE = "movie_release_date";
		public static final String MOVIE_TITLE = "movie_title";
		public static final String MOVIE_ORIGINAL_TITLE = "movie_original_title";
		public static final String MOVIE_LANGUAGE = "movie_language";
		public static final String MOVIE_BACKDROP_PATH = "movie_backdrop_path";
		public static final String MOVIE_POPULARITY = "movie_popularity";
		public static final String MOVIE_VIDEO = "movie_video";
		public static final String MOVIE_VOTE_AVERAGE = "movie_vote_average";
		public static final String MOVIE_VOTE_COUNT = "movie_vote_count";

		// create content uri
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIES).build();

		// create cursor of base type directory for multiple entries
		public static final String CONTENT_DIR_TYPE =
		ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

		// create cursor of base type item for single entry
		public static final String CONTENT_ITEM_TYPE =
			ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

		// for building URIs on insertion
		public static Uri buildUri(long id){
        		return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}
}
