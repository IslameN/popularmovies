package com.misla.popularmovies.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.misla.popularmovies.internet.model.Movie;
import com.misla.popularmovies.internet.model.Review;
import com.misla.popularmovies.internet.model.Trailer;
import com.misla.popularmovies.R;
import com.misla.popularmovies.view.adapter.ReviewAdapter;
import com.misla.popularmovies.view.viewmodel.ReviewViewModel;
import com.misla.popularmovies.view.viewmodel.TrailerViewModel;
import com.misla.popularmovies.view.adapter.TrailersAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Fragment that displays the movie details.
 */
public class MovieDetailFragment extends Fragment implements TrailersAdapter.OnTrailerClickListener
                , ReviewAdapter.OnReviewClickListener {
    /**
     * Tag for debugging purposes.
     */
    private static final String TAG = MovieDetailFragment.class.getSimpleName();

    /**
     * Movie vote average max value.
     */
    private static final String VOTE_PROPORTION = " / 10";

    /**
     * Movie to be detail displayed,
     */
    private static Movie movie;

    /**
     * Title for the trailers.
     */
    private TextView trailersTitle;

    /**
     * Adapter for the trailers of the selected movie.
     */
    private TrailersAdapter trailersAdapter;

    /**
     * Recycler view for trailers.
     */
    private RecyclerView trailersRecycler;

    /**
     * Adapter for the reviews of the selected movie.
     */
    private ReviewAdapter reviewAdapter;

    /**
     * Recycler view for reviews.
     */
    private RecyclerView reviewsRecycler;

    /**
     * Title for the reviews.
     */
    private TextView reviewsTitle;
    /**
     * Sets the movie to be displayed.
     * @param movie Movie to be displayed.
     */
    public static void setMovie(Movie movie) {
        MovieDetailFragment.movie = movie;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ImageView imageView = view.findViewById(R.id.iv_movie_detail);

        Picasso.get()
                .load(movie.getFullPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "An error occurred: " + e.toString());
                    }
                });

        // Title
        TextView title = view.findViewById(R.id.tv_movie_detail_title);
        title.setText(String.valueOf(movie.getTitle()));

        // Release date
        TextView releaseDate = view.findViewById(R.id.tv_movie_detail_release_date);
        releaseDate.setText(movie.getReleaseDate());

        // Average
        TextView voteAverage = view.findViewById(R.id.tv_movie_detail_vote_average);
        String average = String.valueOf(movie.getVoteAverage()) + VOTE_PROPORTION;
        voteAverage.setText(average);

        // Overview
        TextView overView = view.findViewById(R.id.tv_movie_detail_overview);
        overView.setText(movie.getOverview());

        trailersTitle = view.findViewById(R.id.tv_trailers);

        getTrailers();

        trailersAdapter = new TrailersAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        trailersRecycler = view.findViewById(R.id.rv_trailers);
        trailersRecycler.setLayoutManager(linearLayoutManager);
        trailersRecycler.setAdapter(trailersAdapter);

        reviewsTitle = view.findViewById(R.id.tv_reviews);

        getReviews();

        reviewAdapter = new ReviewAdapter(this);
        LinearLayoutManager linearLayoutManagerReviews = new LinearLayoutManager(getActivity());
        linearLayoutManagerReviews.setOrientation(LinearLayoutManager.VERTICAL);

        reviewsRecycler = view.findViewById(R.id.rv_reviews);
        reviewsRecycler.setLayoutManager(linearLayoutManagerReviews);
        reviewsRecycler.setAdapter(reviewAdapter);

        return view;
    }

    /**
     * Gets the trailers of the movie.
     */
    private void getTrailers() {
        TrailerViewModel model = ViewModelProviders.of(this).get(TrailerViewModel.class);
        model.getTrailers(movie.getId()).observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                if (trailers != null && trailers.size() > 0) {
                    trailersAdapter.setTrailerList(trailers);
                    trailersRecycler.setVisibility(View.VISIBLE);
                } else {
                    trailersRecycler.setVisibility(View.GONE);
                    trailersTitle.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Gets the reviews of the movie.
     */
    private void getReviews() {
        ReviewViewModel model = ViewModelProviders.of(this).get(ReviewViewModel.class);
        model.getReviews(movie.getId()).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                if (reviews != null && reviews.size() > 0) {
                    reviewAdapter.setReviewList(reviews);
                    reviewsRecycler.setVisibility(View.VISIBLE);
                } else {
                    reviewsRecycler.setVisibility(View.GONE);
                    reviewsTitle.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Opens YouTube if the user clicks on the trailer.
     * @param context Context needed to start activities.
     * @param id Id of the YouTube trailer.
     */
    private static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    /**
     * Opens a browser to read a review.
     * @param context Context needed to start activities.
     * @param url Url of the review to be read.
     */
    private static void openBrowser(Context context, String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        context.startActivity(webIntent);
    }

    @Override
    public void OnTrailerClick(Trailer trailer) {
        watchYoutubeVideo(getActivity(), trailer.getKey());
    }

    @Override
    public void OnReviewClick(Review review) {
        openBrowser(getActivity(), review.getUrl());
    }
}
