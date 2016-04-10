package com.vipul.popularmovies.core;

import com.android.volley.Response;

/**
 * Created by HP-HP on 27-03-2016.
 */
public interface ResponseListener<T> extends Response.Listener<T>, Response.ErrorListener{
}
