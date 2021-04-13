package com.misla.popularmovies.internet.services;

import com.misla.popularmovies.internet.results.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for the retrofit call for movie requests.
 */
public interface MovieDataService {
    /**
     * Gets the most popular movies.
     * @param page Page to be queried.
     * @param key Key needed to perform the query.
     * @return Call with the most popular movies.
     */
    @GET("movie/popular")
    Call<MovieResults> getPopularMovies(@Query("page") int page, @Query("api_key") String key);

    /**
     * Gets the top rated movies.
     * @param page Page to be queried.
     * @param key Key needed to perform the query.
     * @return Call with the top rated movies.
     */
    @GET("movie/top_rated")
    Call<MovieResults> getTopRatedMovies(@Query("page") int page, @Query("api_key") String key);
}
