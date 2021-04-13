package com.misla.popularmovies.view.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.misla.popularmovies.R;
import com.misla.popularmovies.internet.model.Trailer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the recycler view of movies.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    /**
     * Listener to handel clicks.
     */
    public interface OnTrailerClickListener {
        void OnTrailerClick(Trailer trailer);
    }

    /**
     * Tag for debugging purposes.
     */
    private static final String TAG = MoviesAdapter.class.getSimpleName();

    /**
     * Aspect ratio of the trailer ImageView.
     */
    private static final float ASPECT_RATIO = 3f / 4;

    /**
     * Width multiplier of the trailer ImageView.
     */
    private static final float MULTIPLIER = .75f;

    /**
     * Arrays of trailer to be shown.
     */
    private List<Trailer> trailers;

    /**
     * Click listener.
     */
    private OnTrailerClickListener onTrailerClickListener;

    /**
     * Constructor of the class.
     */
    public TrailersAdapter(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.trailer_item, viewGroup, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int position) {
        // Image view is resized to avoid empty spaces between them.
        int width = (int)(getScreenWidth() * MULTIPLIER);
        int height = (int)(width * ASPECT_RATIO);
        trailerViewHolder.imageView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        Picasso picasso = Picasso.get();
        picasso.load(getYoutubeThumbnailPath(trailers.get(position).getKey()))
                .into(trailerViewHolder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, e.toString());
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (trailers == null) return 0;
        return trailers.size();
    }

    /**
     * Sets the trailer list to be displayed.
     * @param trailers list of trailers to be displayed.
     */
    public void setTrailerList(List<Trailer> trailers) {
        this.trailers = trailers;

        notifyDataSetChanged();
    }

    /**
     * Gets the thumbnail path of a YouTube thumbnail video.
     * @param key Youtube key for the video.
     * @return Full path to the YouTube video.
     */
    private String getYoutubeThumbnailPath(String key) {
        return "https://img.youtube.com/vi/" + key + "/hqdefault.jpg";
    }

    /**
     * Gets the screen width.
     * @return screen width of the device.
     */
    private static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * Class that holds the recycling.
     */
    public final class TrailerViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /**
         * Image view that displays the movie.
         */
        private ImageView imageView;

        /**
         * Constuctor of the class.
         * @param view View to be drawn.
         */
        TrailerViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_trailer);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Trailer trailer = trailers.get(getAdapterPosition());
            onTrailerClickListener.OnTrailerClick(trailer);
        }
    }
}
