package com.vipul.popularmovies.activity.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vipul.popularmovies.R;
import com.vipul.popularmovies.model.Movies;
import com.vipul.popularmovies.utils.ImageLoadingUtils;

import java.util.List;

/**
 * Created by HP-HP on 29-03-2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Callbacks {
        public void onMovieClick(Movies movies);
    }

    private Callbacks mCallbacks;
    private Context context;
    private List<Movies> mFeedList;

    public MoviesAdapter(List<Movies> feedList) {
        this.mFeedList = feedList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = View.inflate(parent.getContext(), R.layout.item_movie, null);
        //view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MovieViewHolder) {
            final Movies movies = mFeedList.get(position);

            final MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

            movieViewHolder.mMovieName.setText(movies.getOriginal_title());

            ImageLoadingUtils.load(movieViewHolder.mMovieImage, "http://image.tmdb.org/t/p/w185/" + movies.getPoster_path());

            movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mCallbacks!=null) {
                        mCallbacks.onMovieClick(movies);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView mMovieName;
        private SimpleDraweeView mMovieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMovieName = (TextView) itemView.findViewById(R.id.movieTextView);
            mMovieImage = (SimpleDraweeView) itemView.findViewById(R.id.movieImage);

        }
    }

}
