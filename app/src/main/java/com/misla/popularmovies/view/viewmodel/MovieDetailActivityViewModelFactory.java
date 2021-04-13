package com.misla.popularmovies.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.misla.popularmovies.database.AppDatabase;

// TODO: try to solved the same way it is done in reviews and trailes.
/**
 * Factory class for @MovieDetailActivityViewModel.
 */
public class MovieDetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    /**
     * Database to look into.
     */
    private final AppDatabase appDatabase;

    /**
     * Id of the movie.
     */
    private final int id;

    /**
     * Contructor of the factory.
     * @param appDatabase Database to look into.
     * @param id Id of the movie.
     */
    public MovieDetailActivityViewModelFactory(AppDatabase appDatabase, int id) {
        this.appDatabase = appDatabase;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailActivityViewModel(appDatabase, id);
    }
}