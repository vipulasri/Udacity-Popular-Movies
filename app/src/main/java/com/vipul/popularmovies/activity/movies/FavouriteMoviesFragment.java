package com.vipul.popularmovies.activity.movies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vipul.popularmovies.IntentRequestCodes;
import com.vipul.popularmovies.R;
import com.vipul.popularmovies.activity.movies_details.MoviesDetailsActivity;
import com.vipul.popularmovies.database.MoviesContract;
import com.vipul.popularmovies.database.MoviesOpenHelper;
import com.vipul.popularmovies.event.FavoriteChangeEvent;
import com.vipul.popularmovies.event.MovieSelectedEvent;
import com.vipul.popularmovies.model.Movies;
import com.vipul.popularmovies.model.Sort;
import com.vipul.popularmovies.utils.LocalStoreUtil;
import com.vipul.popularmovies.utils.Utils;
import com.vipul.popularmovies.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteMoviesFragment extends BaseMovieFragment implements MoviesAdapter.Callbacks, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MoviesAdapter moviesAdapter;
    private List<Movies> mMovies = new ArrayList<>();
    private View noFavoriteView;

    private static final int CURSOR_LOADER_ID = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {

        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        super.onDetach();
    }

    public FavouriteMoviesFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noFavoriteView = (View) view.findViewById(R.id.layout_no_favorite);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        int columnCount = getResources().getInteger(R.integer.movies_columns);

        gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        int spacing = Utils.dpToPx(5, getActivity()); // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(columnCount, spacing, includeEdge));
        recyclerView.setLayoutManager(gridLayoutManager);

        initAdapter(mMovies);
    }

    private void initAdapter(List<Movies> movies) {
        showNoFavorite(false);
        moviesAdapter = new MoviesAdapter(movies);
        moviesAdapter.setCallbacks(this);
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    public void onMovieClick(Movies movies) {
        EventBus.getDefault().post(new MovieSelectedEvent(movies));
    }

    @Override
    public void onFavoriteClick(Movies movies) {
        if(movies.isFavorite()) { // Already added is removed
            LocalStoreUtil.removeFromFavorites(getActivity(), movies.getId());
            ViewUtils.showToast(getResources().getString(R.string.removed_favorite),getActivity());

            getActivity().getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movies.getId())).build(), null, null);

            getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);

        } else {
            LocalStoreUtil.addToFavorites(getActivity(), movies.getId());
            ViewUtils.showToast(getResources().getString(R.string.added_favorite),getActivity());

            ContentValues values = MoviesOpenHelper.getMovieContentValues(movies);
            getActivity().getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, values);
        }

        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Loader is finished loading data

        if(data.getCount()>0) {
            initAdapter(getMoviesFromCursor(data));
        } else {
            showNoFavorite(true);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Loader is reset, assign data to null
    }

    private List<Movies> getMoviesFromCursor(Cursor cursor) {

        List<Movies> movies = new ArrayList<>();

        if (cursor != null) {
            /*Log.e("cursor length","->"+cursor.getCount());
            Log.e("column length","->"+cursor.getColumnCount());*/

            if (cursor.moveToFirst()){
                do{

                    int movie_id = cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_ID));
                    boolean movie_adult = cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_ADULT))==1;
                    String movie_poster_path = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_POSTER_PATH));
                    String movie_overview = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_OVERVIEW));
                    String movie_release_date = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_RELEASE_DATE));
                    String genre = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_GENRES));

                    List<Integer> movie_genre = new ArrayList<>();
                    for (String s : genre.split(","))
                        movie_genre.add(Integer.parseInt(s));

                    String movie_title = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_TITLE));
                    String movie_original_title = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_ORIGINAL_TITLE));
                    String movie_language = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_LANGUAGE));
                    String movie_backdrop_path = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_BACKDROP_PATH));
                    String movie_popularity = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_POPULARITY));
                    boolean movie_video = cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_VIDEO))==1;
                    String movie_vote_average = cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_VOTE_AVERAGE));
                    int movie_vote_count = cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.MOVIE_VOTE_COUNT));

                    Movies movie = new Movies(movie_id,movie_adult, movie_poster_path, movie_overview, movie_release_date, movie_genre
                            ,movie_original_title,movie_language,movie_title,movie_backdrop_path,movie_popularity,movie_video,movie_vote_average,movie_vote_count);

                    movies.add(movie);

                }while(cursor.moveToNext());
            }

        }

        return movies;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final FavoriteChangeEvent event){
        Log.e("onEvent","->"+event.isFavoriteChanged());

        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);

    }

    private void showNoFavorite(boolean value){

        int noFavoriteVisibility = value? View.VISIBLE : View.GONE;
        noFavoriteView.setVisibility(noFavoriteVisibility);

        int recyclerViewVisibility = value? View.GONE : View.VISIBLE;
        recyclerView.setVisibility(recyclerViewVisibility);
    }

}
