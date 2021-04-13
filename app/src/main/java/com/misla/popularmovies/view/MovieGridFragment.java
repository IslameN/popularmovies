package com.misla.popularmovies.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.misla.popularmovies.BuildConfig;
import com.misla.popularmovies.internet.model.Movie;
import com.misla.popularmovies.R;
import com.misla.popularmovies.internet.services.MovieDataService;
import com.misla.popularmovies.internet.results.MovieResults;
import com.misla.popularmovies.internet.MyRetrofit;
import com.misla.popularmovies.view.adapter.MoviesAdapter;
import com.misla.popularmovies.view.viewmodel.MovieGridViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment that displays a grid of available movies.
 */
public class MovieGridFragment extends Fragment implements MoviesAdapter.OnMovieClickListener {
    /**
     * Tag for debugging purposes.
     */
    private static final String TAG = MovieGridFragment.class.getSimpleName();

    /**
     * Recycler view of the movies.
     */
    private RecyclerView recyclerViewMovies;

    /**
     * Adapter for the movies.
     */
    private MoviesAdapter moviesAdapter;

    /**
     * Progress bar for loading movies.
     */
    private ProgressBar progressBar;

    /**
     * Retrofit call.
     */
    private Call<MovieResults> call;

    /**
     * Enumeration for sorting options.
     */
    private enum SortMovie {
        POPULARITY,
        TOP_RATED,
        FAVOURITE
    }

    /**
     * Initial sort mode.
     */
    private SortMovie sortMovie = SortMovie.POPULARITY;

    /**
     * List of movies currently displayed.
     */
    private List<Movie> movies;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        try {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        } catch (NullPointerException nullPointer) {
            Log.e(TAG, nullPointer.toString());
        }

        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.pb_loading_movies);

        moviesAdapter = new MoviesAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerViewMovies = view.findViewById(R.id.rv_movies);
        recyclerViewMovies.setLayoutManager(gridLayoutManager);
        recyclerViewMovies.setAdapter(moviesAdapter);

        if (savedInstanceState != null) {
            showLoading();
            sortMovie = (SortMovie)savedInstanceState.get(getString(R.string.bundle_sort));
            if (sortMovie == SortMovie.FAVOURITE) {
                likedMovies();
            } else {
                movies = savedInstanceState.getParcelableArrayList(getString(R.string.bundle_movies));
                moviesAdapter.setMoviesList(movies);
                showMovies();
            }
        } else {
            if (networkStatus(getActivity())) {
                makeCall();
            } else {
                showError();
            }
        }

        changeTitle();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(getString(R.string.bundle_sort), sortMovie);
        if (movies != null) {
            outState.putParcelableArrayList(getString(R.string.bundle_movies), new ArrayList<Parcelable>(movies));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_grid, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                return true;
            case R.id.sort_by_popularity:
                sortMovie = SortMovie.POPULARITY;
                makeCall();
                return true;
            case R.id.sort_by_top:
                sortMovie = SortMovie.TOP_RATED;
                makeCall();
                return true;
            case R.id.sort_by_favorites:
                sortMovie = SortMovie.FAVOURITE;
                makeCall();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnMovieClick(Movie movie) {
        Intent movieDetailActivity = new Intent(getActivity(), MovieDetailActivity.class);
        movieDetailActivity.putExtra(getString(R.string.movie_parcel), movie);
        startActivity(movieDetailActivity);
    }

    /**
     * Changes activity title depending on selected sorting option.
     */
    private void changeTitle() {
        String title = getString(R.string.title_activity_popular_movies);
        switch (sortMovie) {
            case POPULARITY:
                title = getString(R.string.title_activity_popular_movies);
                break;
            case TOP_RATED:
                title = getString(R.string.title_activity_top_rated_movies);
                break;
            case FAVOURITE:
                title = getString(R.string.title_activity_favorite_movies);
                break;
            default:
                break;
        }

        try {
            getActivity().setTitle(title);
        } catch (NullPointerException nullPointer) {
            Log.e(TAG, nullPointer.getMessage());
        }
    }

    /**
     * Makes call depending on selected sorting option.
     */
    private void makeCall() {
        changeTitle();
        switch (sortMovie) {
            case POPULARITY:
                makePopularCall();
                break;
            case TOP_RATED:
                makeTopRatedCall();
                break;
            case FAVOURITE:
                likedMovies();
                break;
            default:
                makePopularCall();
                break;
        }
    }

    /**
     * Makes the most popular movies call.
     */
    private void makePopularCall() {
        showLoading();

        MovieDataService movieDBHelper = MyRetrofit.getRetrofitInstance().create(MovieDataService.class);
        call = movieDBHelper.getPopularMovies(1, BuildConfig.APIKEY);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(@NonNull Call<MovieResults> call, @NonNull Response<MovieResults> response) {
                if (response.code() == 200) {
                    populateAdapter(response);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResults> call, @NonNull Throwable t) {
                Log.e(TAG, "ERROR: " + t.toString());
                showError();
            }
        });
    }

    /**
     * Makes the top rated movies call.
     */
    private void makeTopRatedCall() {
        showLoading();

        MovieDataService movieDBHelper = MyRetrofit.getRetrofitInstance().create(MovieDataService.class);
        call = movieDBHelper.getTopRatedMovies(1, BuildConfig.APIKEY);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(@NonNull Call<MovieResults> call, @NonNull Response<MovieResults> response) {
                if (response.code() == 200) {
                    populateAdapter(response);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResults> call, @NonNull Throwable t) {
                Log.e(TAG, "ERROR: " + t.toString());
                showError();
            }
        });
    }

    /**
     * Makes the top rated movies call.
     */
    private void likedMovies() {
        showLoading();

        MovieGridViewModel viewModel = ViewModelProviders.of(MovieGridFragment.this).get(MovieGridViewModel.class);
        viewModel.getMovies().observe(MovieGridFragment.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                if (sortMovie == SortMovie.FAVOURITE) {
                    movies = movieList;
                    moviesAdapter.setMoviesList(movies);
                    recyclerViewMovies.setAdapter(moviesAdapter);
                }
            }
        });

        showMovies();
    }

    /**
     * Populates adapter with response movies.
     * @param response Response with the movies queried.
     */
    private void populateAdapter(Response<MovieResults> response) {
        try {
            movies = response.body().getMovieResult();

            moviesAdapter.setMoviesList(movies);
            recyclerViewMovies.setAdapter(moviesAdapter);

            showMovies();
        } catch (NullPointerException nullPointer) {
            Log.e(TAG, nullPointer.toString());

            showError();
        }
    }

    /**
     * Shows an error if it is not possible to retrieve the movies.
     */
    private void showError() {
        showMovies();

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.error)
                .setMessage(R.string.no_internet)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
        .show();
    }

    /**
     * Shows movies grid and removes loading.
     */
    private void showMovies() {
        progressBar.setVisibility(View.GONE);
        recyclerViewMovies.setVisibility(View.VISIBLE);
    }

    /**
     * Shows loading and removes grid.
     */
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewMovies.setVisibility(View.GONE);
    }

    /**
     * Check the network status.
     * @param context Context needed to check the status.
     * @return True if network is available, false otherwise.
     */
    private static Boolean networkStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (NullPointerException nullPointer) {
            Log.e(TAG, nullPointer.toString());
            return false;
        }
        return false;
    }
}
