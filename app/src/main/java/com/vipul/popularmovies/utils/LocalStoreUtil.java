package com.vipul.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by HP-HP on 30-05-2016.
 */
public class LocalStoreUtil {
    private static GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Gson gson = gsonBuilder.create();

    public static final String PREF_FILE_NAME = "com.vipul.popularmovies";
    public static final String PREF_FAVORITE_MOVIES = "favorite_movies";

    public static void addToFavorites(final Context context, long movieId) {
        SharedPreferences sp = null;
        try {
            sp = getSharedPreference(context);
            Set<String> set = sp.getStringSet(PREF_FAVORITE_MOVIES, null);
            if (set == null) set = new HashSet<>();
            set.add(String.valueOf(movieId));

            SharedPreferences.Editor editor = getSharedEditor(context);
            editor.clear();

            editor.putStringSet(PREF_FAVORITE_MOVIES, set).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFromFavorites(final Context context, long movieId) {
        SharedPreferences sp = null;
        try {
            sp = getSharedPreference(context);
            Set<String> set = sp.getStringSet(PREF_FAVORITE_MOVIES, null);
            if (set == null) set = new HashSet<>();
            set.remove(String.valueOf(movieId));

            SharedPreferences.Editor editor = getSharedEditor(context);
            editor.clear();

            editor.putStringSet(PREF_FAVORITE_MOVIES, set).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean hasInFavorites(final Context context, long movieId) {
        SharedPreferences sp = null;
        boolean isfav = false;
        try {
            sp = getSharedPreference(context);
            Set<String> set = sp.getStringSet(PREF_FAVORITE_MOVIES, null);
            if (set == null) set = new HashSet<>();
            isfav = set.contains(String.valueOf(movieId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isfav;
    }

    public static List<String> getFavorites(final Context context) {

        List<String> favs = null;
        try {
            SharedPreferences pref = getSharedPreference(context);

            Set<String> set = pref.getStringSet(PREF_FAVORITE_MOVIES, Collections.EMPTY_SET);
            favs = new ArrayList<>();

            if (set.isEmpty()) {
                favs = null;
            } else {
                for (String s : set) {
                    String user = gson.fromJson(s, String.class);
                    favs.add(user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return favs;
    }

    public static void clearSession(Context context) {
        try {
            SharedPreferences.Editor editor = getSharedEditor(context);
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SharedPreferences.Editor getSharedEditor(Context context)
            throws Exception {
        if (context == null) {
            throw new Exception("Context null Exception");
        }
        return getSharedPreference(context).edit();
    }

    private static SharedPreferences getSharedPreference(Context context)
            throws Exception {
        if (context == null) {
            throw new Exception("Context null Exception");
        }
        return context.getSharedPreferences(PREF_FILE_NAME, 0);
    }

}
