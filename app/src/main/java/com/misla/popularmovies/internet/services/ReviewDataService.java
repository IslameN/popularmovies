package com.misla.popularmovies.internet.services;

import com.misla.popularmovies.internet.results.ReviewResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for the retrofit call.
 */
public interface ReviewDataService {
    /**
     * Gets the reviews of the selected movie.
     * @param id Movie to fetch reviews.
     * @param key Key to perform the request.
     * @return Call with the reviews.
     */
    @GET("movie/{id}/reviews")
    Call<ReviewResults> getReviews(@Path("id") long id, @Query("api_key") String key);
}
