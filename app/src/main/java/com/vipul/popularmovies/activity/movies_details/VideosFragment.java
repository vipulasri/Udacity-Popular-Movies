package com.vipul.popularmovies.activity.movies_details;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.vipul.popularmovies.Config;
import com.vipul.popularmovies.PopularMoviesService;
import com.vipul.popularmovies.R;
import com.vipul.popularmovies.activity.movies.GridSpacingItemDecoration;
import com.vipul.popularmovies.core.ResponseListener;
import com.vipul.popularmovies.dto.MovieVideoResponse;
import com.vipul.popularmovies.event.MovieVideosEvent;
import com.vipul.popularmovies.model.MovieVideos;
import com.vipul.popularmovies.model.Movies;
import com.vipul.popularmovies.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-HP on 19-05-2016.
 */
public class VideosFragment extends BaseMovieDetailsFragment implements ResponseListener<MovieVideoResponse>, VideosAdapter.Callbacks{

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private VideosAdapter videosAdapter;
    private List<MovieVideos> mVideos = new ArrayList<>();
    private Movies movies;

    public static VideosFragment newInstance(Movies movies){
        if (movies == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable(Movies.TAG_MOVIES, movies);

        VideosFragment fragment = new VideosFragment();
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

            new PopularMoviesService().getMovieVideos(String.valueOf(movies.getId()), this);

            //showProgressDialog();
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
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        int columnCount = getResources().getInteger(R.integer.videos_columns);

        gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        int spacing = Utils.dpToPx(5, getActivity()); // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(columnCount, spacing, includeEdge));
        recyclerView.setLayoutManager(gridLayoutManager);

        initAdapter(mVideos);

    }

    private void initAdapter(List<MovieVideos> movieVideos) {
        videosAdapter = new VideosAdapter(movieVideos);
        videosAdapter.setCallbacks(this);
        recyclerView.setAdapter(videosAdapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("error", "->" + error);
    }

    @Override
    public void onResponse(MovieVideoResponse response) {

        if(response==null || response.getResults().isEmpty()) {
            return;
        }

        initAdapter(response.getResults());

        EventBus.getDefault().post(new MovieVideosEvent(response.getResults().get(0)));

    }

    @Override
    public void onVideoClick(MovieVideos movieVideos) {

        inflateVideoPlayer(movieVideos.getKey());

    }

    private void inflateVideoPlayer(String videoKey) {

        int startTimeMillis = 0;
        boolean autoplay = true;
        boolean lightboxMode = false;

        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                getActivity(), Config.Google_Key, videoKey, startTimeMillis, autoplay, lightboxMode);

        if (intent != null) {
            if (canResolveIntent(intent)) {
                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            } else {
                // Could not resolve the intent - must need to install or update the YouTube API service.
                YouTubeInitializationResult.SERVICE_MISSING
                        .getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != getActivity().RESULT_OK) {
            YouTubeInitializationResult errorReason =
                    YouTubeStandalonePlayer.getReturnedInitializationResult(data);
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(getActivity(), 0).show();
            } else {
                String errorMessage = getResources().getString(R.string.player_error) + errorReason.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }
}
