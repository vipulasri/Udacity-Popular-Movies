package com.vipul.popularmovies.activity.movies_details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.okhttp.Response;
import com.vipul.popularmovies.PopularMoviesService;
import com.vipul.popularmovies.R;
import com.vipul.popularmovies.core.ResponseListener;
import com.vipul.popularmovies.dto.MovieReviewResponse;
import com.vipul.popularmovies.model.MovieReviews;
import com.vipul.popularmovies.model.MovieVideos;
import com.vipul.popularmovies.model.Movies;
import com.vipul.popularmovies.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-HP on 19-05-2016.
 */
public class ReviewsFragment extends BaseMovieDetailsFragment implements ResponseListener<MovieReviewResponse>{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ReviewsAdapter reviewsAdapter;
    private List<MovieReviews> mReviews = new ArrayList<>();
    private Movies movies;
    private View noReviewsView;

    public static ReviewsFragment newInstance(Movies movies){
        if (movies == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable(Movies.TAG_MOVIES, movies);

        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movies = getArguments().getParcelable(Movies.TAG_MOVIES);
        getVideosData();
    }

    public void getVideosData() {
        if(isInternetAvailable()) {

            new PopularMoviesService().getMovieReviews(String.valueOf(movies.getId()), this);

        } else {
            Snackbar snackbar = Snackbar
                    .make(getCoordinatorLayout(), R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getVideosData();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        noReviewsView = view.findViewById(R.id.layout_no_reviews);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        int spacing = Utils.dpToPx(5, getActivity());
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacing));

        initAdapter(mReviews);
    }

    private void initAdapter(List<MovieReviews> movieReviews) {
        showNoReviews(false);
        reviewsAdapter = new ReviewsAdapter(movieReviews);
        recyclerView.setAdapter(reviewsAdapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("error", "->" + error);
    }

    @Override
    public void onResponse(MovieReviewResponse response) {
        if(response==null) {
            return;
        }

        if(response.getTotal_results()>0) {
            initAdapter(response.getResults());
        } else {
            showNoReviews(true);
        }

    }

    private void showNoReviews(boolean value){

        int noReviewsVisibility = value? View.VISIBLE : View.GONE;
        noReviewsView.setVisibility(noReviewsVisibility);

        int recyclerViewVisibility = value? View.GONE : View.VISIBLE;
        recyclerView.setVisibility(recyclerViewVisibility);
    }
}
