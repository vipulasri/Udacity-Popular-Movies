package com.vipul.popularmovies.activity.movies;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import com.vipul.popularmovies.utils.NetworkUtils;

/**
 * Created by HP-HP on 01-04-2016.
 */
public class BaseMovieFragment extends Fragment {


    public void showProgressDialog() {
        ((HomeActivity) getActivity()).showProgressDialog();
    }

    public void hideProgressDialog() {
        ((HomeActivity) getActivity()).hideProgressDialog();
    }

    public boolean isInternetAvailable() {
        return NetworkUtils.isNetworkConnected(getActivity());
    }

    public void showSnackBar(String value) {
        ((HomeActivity) getActivity()).showSnackBar(value);
    }

    public void showSnackBar(int value) {
        ((HomeActivity) getActivity()).showSnackBar(value);
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return ((HomeActivity) getActivity()).getCoordinatorLayout();
    }

}
