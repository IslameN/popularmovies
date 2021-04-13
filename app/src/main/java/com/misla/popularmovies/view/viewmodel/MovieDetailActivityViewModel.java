package com.misla.popularmovies.view.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.misla.popularmovies.internet.model.Movie;
import com.misla.popularmovies.database.AppDatabase;

/**
 * View model for to check if the movie is liked or not.
 */
public class MovieDetailActivityViewModel extends ViewModel {
    /**
     * Movie liked.
     */
    private LiveData<Movie> movie;

    /**
     * Gets the movie liked.
     * @return Movie liked if it is liked.
     */
    public LiveData<Movie> getMovie() {
        return movie;
    }

    /**
     * Contructor of the view model.
     * @param appDatabase Database to look into.
     * @param id Id of the movie.
     */
    public MovieDetailActivityViewModel(AppDatabase appDatabase, int id) {
        movie = appDatabase.movieDao().isFavorite(id);
    }
}
