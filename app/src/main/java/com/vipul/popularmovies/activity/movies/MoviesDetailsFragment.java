package com.vipul.popularmovies.activity.movies;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vipul.popularmovies.R;
import com.vipul.popularmovies.activity.movies_details.MoviesDetailsAdapter;
import com.vipul.popularmovies.database.MoviesContract;
import com.vipul.popularmovies.database.MoviesOpenHelper;
import com.vipul.popularmovies.event.FavoriteChangeEvent;
import com.vipul.popularmovies.model.Movies;
import com.vipul.popularmovies.utils.GenreHelper;
import com.vipul.popularmovies.utils.ImageLoadingUtils;
import com.vipul.popularmovies.utils.LocalStoreUtil;
import com.vipul.popularmovies.utils.Utils;
import com.vipul.popularmovies.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

public class MoviesDetailsFragment extends BaseMovieFragment {

    private static final String TAG_MOVIES = "movies";

    private Movies movies;
    private SimpleDraweeView mHeaderImage, mMoviePosterImage;
    private TextView mMovieTitle, mMovieGenre;
    private FloatingActionButton mFavoriteButton;

    private ViewPager mViewPager;
    private MoviesDetailsAdapter mMoviesDetailsAdapter;
    private TabLayout tabLayout;
    private boolean isFavoriteChanged = false;

    //Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

    //Review and Trailers

    public static MoviesDetailsFragment newInstance(@NonNull Movies movies) {
        Bundle args = new Bundle();
        args.putParcelable(TAG_MOVIES, movies);

        MoviesDetailsFragment fragment = new MoviesDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movies = (Movies) getArguments().getParcelable(TAG_MOVIES);

        mFavoriteButton = (FloatingActionButton) view.findViewById(R.id.favButton);
        mHeaderImage = (SimpleDraweeView) view.findViewById(R.id.headerImage);
        mMoviePosterImage = (SimpleDraweeView) view.findViewById(R.id.moviePosterImage);
        mMovieTitle = (TextView) view.findViewById(R.id.movieTitle);
        mMovieGenre = (TextView) view.findViewById(R.id.movieGenre);

        mMoviesDetailsAdapter = new MoviesDetailsAdapter(getChildFragmentManager(), movies);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mMoviesDetailsAdapter);

        tabLayout.setTabTextColors(getResources().getColor(R.color.colorGrey100), getResources().getColor(R.color.primaryText));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.primaryText));
        tabLayout.setSelectedTabIndicatorHeight(Utils.dpToPx(2, getActivity()));
        tabLayout.setupWithViewPager(mViewPager);

        inflateData();
    }


    private void inflateData() {

        ImageLoadingUtils.load(mHeaderImage, "http://image.tmdb.org/t/p/w500/" + movies.getBackdrop_path());
        ImageLoadingUtils.load(mMoviePosterImage, "http://image.tmdb.org/t/p/w185/" + movies.getPoster_path());
        mMovieTitle.setText(movies.getTitle());
        mMovieGenre.setText(GenreHelper.getGenreNamesList(movies.getGenre_ids()).trim());
        mFavoriteButton.setSelected(movies.isFavorite());

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(movies.isFavorite()) { // Already added is removed
                    LocalStoreUtil.removeFromFavorites(getActivity(), movies.getId());
                    getActivity().getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movies.getId())).build(), null, null);

                    ViewUtils.showToast(getResources().getString(R.string.removed_favorite),getActivity());
                    movies.setFavorite(false);

                } else {
                    LocalStoreUtil.addToFavorites(getActivity(), movies.getId());
                    ContentValues values = MoviesOpenHelper.getMovieContentValues(movies);
                    getActivity().getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, values);

                    ViewUtils.showToast(getResources().getString(R.string.added_favorite),getActivity());
                    movies.setFavorite(true);
                }

                isFavoriteChanged = true;
                mFavoriteButton.setSelected(movies.isFavorite());

                EventBus.getDefault().post(new FavoriteChangeEvent(isFavoriteChanged));

            }
        });

    }



}
