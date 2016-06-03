package com.vipul.popularmovies.activity.movies_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vipul.popularmovies.R;
import com.vipul.popularmovies.model.MovieVideos;
import com.vipul.popularmovies.model.Movies;
import com.vipul.popularmovies.utils.GenreHelper;
import com.vipul.popularmovies.utils.ImageLoadingUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by HP-HP on 29-03-2016.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    public interface Callbacks {
        public void onVideoClick(MovieVideos movieVideos);
    }

    private Callbacks mCallbacks;
    private Context context;
    private List<MovieVideos> mFeedList;

    public VideosAdapter(List<MovieVideos> feedList) {
        this.mFeedList = feedList;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = View.inflate(parent.getContext(), R.layout.item_video, null);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, int position) {
            final MovieVideos movieVideos = mFeedList.get(position);

            ImageLoadingUtils.load(holder.mVideoContainer, "http://img.youtube.com/vi/" + movieVideos.getKey() + "/0.jpg");
            holder.mVideoTitle.setText(movieVideos.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mCallbacks!=null) {
                        mCallbacks.onVideoClick(movieVideos);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mVideoContainer;
        private TextView mVideoTitle;

        public VideosViewHolder(View itemView) {
            super(itemView);
            mVideoContainer = (SimpleDraweeView) itemView.findViewById(R.id.videoThumb);
            mVideoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
        }
    }

}
