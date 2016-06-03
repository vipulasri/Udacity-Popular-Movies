package com.vipul.popularmovies.activity.movies_details;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import com.vipul.popularmovies.utils.NetworkUtils;

/**
 * Created by HP-HP on 01-04-2016.
 */
public class BaseMovieDetailsFragment extends Fragment {


    public void showProgressDialog() {
        ((MoviesDetailsActivity) getActivity()).showProgressDialog();
    }

    public void hideProgressDialog() {
        ((MoviesDetailsActivity) getActivity()).hideProgressDialog();
    }

    public boolean isInternetAvailable() {
        return NetworkUtils.isNetworkConnected(getActivity());
    }

    public void showSnackBar(String value) {
        ((MoviesDetailsActivity) getActivity()).showSnackBar(value);
    }

    public void showSnackBar(int value) {
        ((MoviesDetailsActivity) getActivity()).showSnackBar(value);
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return ((MoviesDetailsActivity) getActivity()).getCoordinatorLayout();
    }

}
