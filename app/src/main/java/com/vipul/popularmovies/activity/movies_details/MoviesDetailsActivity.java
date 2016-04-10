package com.vipul.popularmovies.activity.movies_details;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vipul.popularmovies.R;
import com.vipul.popularmovies.activity.base.BaseActivity;
import com.vipul.popularmovies.model.Movies;
import com.vipul.popularmovies.utils.ImageLoadingUtils;

public class MoviesDetailsActivity extends BaseActivity {

    private Movies movies;
    private SimpleDraweeView mHeaderImage, mMoviePosterImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView mMovieTitle, mMovieReleaseDate, mMovieRating, mMovieOverview;
    private RatingBar mRatingBar;

    //Movie details layout contains title, release date, movie poster, vote average, and plot synopsis. 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        getIntentData();
        initViews();
        inflateData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent!=null) {
            movies = (Movies) intent.getExtras().getParcelable(Movies.TAG_MOVIES);
            setActivityTitle(movies.getTitle());
            //setActivityTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mHeaderImage = (SimpleDraweeView) findViewById(R.id.headerImage);
        mMoviePosterImage = (SimpleDraweeView) findViewById(R.id.moviePosterImage);
        mMovieTitle = (TextView) findViewById(R.id.movieTitle);
        mMovieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        mMovieRating = (TextView) findViewById(R.id.movieRating);
        mMovieOverview = (TextView) findViewById(R.id.movieOverview);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    private void inflateData() {
        //collapsingToolbarLayout.setTitleEnabled(false);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        ImageLoadingUtils.load(mHeaderImage, "http://image.tmdb.org/t/p/w500/" + movies.getBackdrop_path());
        ImageLoadingUtils.load(mMoviePosterImage, "http://image.tmdb.org/t/p/w185/" + movies.getPoster_path());
        mMovieTitle.setText(movies.getTitle());
        mMovieReleaseDate.setText(movies.getRelease_date());

        float rating = (float)Math.round(Double.parseDouble(movies.getVote_average()) * 10) / 10;

        mMovieRating.setText(rating+"/10");
        mRatingBar.setRating(rating);
        mMovieOverview.setText(movies.getOverview());

    }

}
