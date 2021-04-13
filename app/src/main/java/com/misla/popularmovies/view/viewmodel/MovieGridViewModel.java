package com.misla.popularmovies.view.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.misla.popularmovies.internet.model.Movie;
import com.misla.popularmovies.database.AppDatabase;

import java.util.List;

/**
 * View model for the liked movies.
 */
public class MovieGridViewModel extends AndroidViewModel {
    /**
     * List of liked movies.
     */
    private LiveData<List<Movie>> movies;

    /**
     * Constructor of the view model.
     * @param application Application needed.
     */
    public MovieGridViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(getApplication());
        movies = appDatabase.movieDao().favoriteMovies();
    }

    /**
     * Gets the movies liked.
     * @return Liked movies.
     */
    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
