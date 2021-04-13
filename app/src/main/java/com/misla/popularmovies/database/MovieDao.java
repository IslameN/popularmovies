package com.misla.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.misla.popularmovies.internet.model.Movie;

import java.util.List;

/**
 * Creates a Dao for the movies.
 */
@Dao
public interface MovieDao {

    /**
     * Gets the favorite movies from database ordered by vote count.
     * @return favourite movies.
     */
    @Query("SELECT * FROM favourite_movies ORDER BY voteCount")
    LiveData<List<Movie>> favoriteMovies();

    /**
     * Checks if selected movie is favourite or not.
     * @param id Id of the movie.
     * @return Movie if it exists, null otherwise.
     */
    @Query("SELECT * FROM favourite_movies WHERE id = :id")
    LiveData<Movie> isFavorite(int id);

    /**
     * Inserts new movie to database.
     * @param movie Movie to be inserted.
     */
    @Insert
    void insertFavoriteMovie(Movie movie);

    /**
     * Deletes new movie to database.
     * @param movie Movie to be deleted.
     */
    @Delete
    void deleteFavoriteMovie(Movie movie);
}
