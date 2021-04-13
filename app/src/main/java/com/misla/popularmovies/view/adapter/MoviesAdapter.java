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

import com.misla.popularmovies.internet.model.Movie;
import com.misla.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the recycler view of movies.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    /**
     * Listener to handel clicks.
     */
    public interface OnMovieClickListener {
        void OnMovieClick(Movie movie);
    }

    /**
     * Tag for debugging purposes.
     */
    private static final String TAG = MoviesAdapter.class.getSimpleName();

    /**
     * Arrays of movies to be shown.
     */
    private List<Movie> movies;

    /**
     * Click listener.
     */
    private OnMovieClickListener onMovieClickListener;

    /**
     * Constructor of the class.
     */
    public MoviesAdapter(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        // Image view is resized to avoid empty spaces between them.
        movieViewHolder.imageView.setLayoutParams(new FrameLayout.LayoutParams(getScreenWidth() / 2, (int)(getScreenWidth() / 2 * 1.5)));
        Picasso.get()
                .load(movies.get(position).getFullPosterPath())
                .placeholder(R.drawable.placeholder) // While loading image or in error.
                .into(movieViewHolder.imageView, new Callback() {
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
        if (movies == null) return 0;
        return movies.size();
    }

    /**
     * Sets the movie list to be displayed.
     * @param movies list of movies to be displayed.
     */
    public void setMoviesList(List<Movie> movies) {
        this.movies = movies;

        notifyDataSetChanged();
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
    public final class MovieViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /**
         * Image view that displays the movie.
         */
        private ImageView imageView;

        /**
         * Constructor of the class.
         * @param view View to be drawn.
         */
        MovieViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_movie);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = movies.get(getAdapterPosition());
            onMovieClickListener.OnMovieClick(movie);
        }
    }
}
