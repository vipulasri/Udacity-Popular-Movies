package com.vipul.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vipul.popularmovies.model.Movies;

/**
 * Created by HP-HP on 30-05-2016.
 */
public class MoviesOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="MOVIES_DB";
    public static final int DATABASE_VERSION = 2;

    public static final String CREATE_TABLE =
            "create table " + MoviesContract.MoviesEntry.TABLE_MOVIES + " ("
                    + MoviesContract.MoviesEntry.ID + " integer primary key autoincrement, "
                    + MoviesContract.MoviesEntry.MOVIE_ID + " integer , "
                    + MoviesContract.MoviesEntry.MOVIE_ADULT + " integer default 0 , "
                    + MoviesContract.MoviesEntry.MOVIE_POSTER_PATH + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_OVERVIEW + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_GENRES + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_RELEASE_DATE + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_TITLE + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_ORIGINAL_TITLE + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_LANGUAGE + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_BACKDROP_PATH + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_POPULARITY + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_VIDEO + " integer default 0 , "
                    + MoviesContract.MoviesEntry.MOVIE_VOTE_AVERAGE + " text , "
                    + MoviesContract.MoviesEntry.MOVIE_VOTE_COUNT + " integer ) ; ";

    public MoviesOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("MoviesOpenHelper", "Upgrading database from version " + oldVersion + " to " + newVersion + ". OLD DATA WILL BE DESTROYED");

        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_MOVIES);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + MoviesContract.MoviesEntry.TABLE_MOVIES + "'");

        // re-create database
        onCreate(db);
    }

    public static ContentValues getMovieContentValues(Movies movies) {

        ContentValues values = new ContentValues();
        values.put(MoviesContract.MoviesEntry.MOVIE_ID, movies.getId());
        values.put(MoviesContract.MoviesEntry.MOVIE_ADULT, movies.getIsAdult()?1:0);
        values.put(MoviesContract.MoviesEntry.MOVIE_POSTER_PATH, movies.getPoster_path());
        values.put(MoviesContract.MoviesEntry.MOVIE_OVERVIEW, movies.getOverview());

        String genres = "";
        String prefix = "";

        for (int i=0; i<movies.getGenre_ids().size(); ++i) {
            genres += prefix + movies.getGenre_ids().get(i).toString();
            prefix = ",";
        }

        values.put(MoviesContract.MoviesEntry.MOVIE_GENRES, genres);
        values.put(MoviesContract.MoviesEntry.MOVIE_RELEASE_DATE, movies.getRelease_date());
        values.put(MoviesContract.MoviesEntry.MOVIE_TITLE, movies.getTitle());
        values.put(MoviesContract.MoviesEntry.MOVIE_ORIGINAL_TITLE, movies.getOriginal_title());
        values.put(MoviesContract.MoviesEntry.MOVIE_LANGUAGE, movies.getOriginal_language());
        values.put(MoviesContract.MoviesEntry.MOVIE_BACKDROP_PATH, movies.getBackdrop_path());
        values.put(MoviesContract.MoviesEntry.MOVIE_POPULARITY, movies.getPopularity());
        values.put(MoviesContract.MoviesEntry.MOVIE_VIDEO, movies.getIsVideo()?1:0);
        values.put(MoviesContract.MoviesEntry.MOVIE_VOTE_AVERAGE, movies.getVote_average());
        values.put(MoviesContract.MoviesEntry.MOVIE_VOTE_COUNT, movies.getVote_count());

        return values;
    }
}
