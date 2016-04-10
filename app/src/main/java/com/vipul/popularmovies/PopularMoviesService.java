package com.vipul.popularmovies;

import com.google.gson.reflect.TypeToken;
import com.vipul.popularmovies.core.BaseService;
import com.vipul.popularmovies.core.ResponseListener;
import com.vipul.popularmovies.dto.MoviesResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP-HP on 09-12-2015.
 */
public class PopularMoviesService extends BaseService {

     public void getMostPopularMovies(ResponseListener<MoviesResponse> listener) {
        Map<String, String> params = new HashMap<>();
         params.put("api_key", BuildConfig.MOVIEDB_API);
        executePostRequest(ApiEndpoints.GET_MOVIES_POPULAR, null, params, new TypeToken<MoviesResponse>() {
        }, listener);
    }

    public void getTopRatedMovies(ResponseListener<MoviesResponse> listener) {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", BuildConfig.MOVIEDB_API);
        executePostRequest(ApiEndpoints.GET_MOVIES_TOP_RATED, null, params, new TypeToken<MoviesResponse>() {
        }, listener);
    }

}
