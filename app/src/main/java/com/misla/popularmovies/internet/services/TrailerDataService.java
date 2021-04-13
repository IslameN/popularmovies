package com.misla.popularmovies.internet.services;


import com.misla.popularmovies.internet.results.TrailerResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface for the retrofit call for trailer requests.
 */
public interface TrailerDataService {
    /**
     * Gets the trailers of the selected movie.
     * @param id Movie to fetch trailers.
     * @param key Key to perform the request.
     * @return Call with the trailers.
     */
    @GET("movie/{id}/videos")
    Call<TrailerResults> getTrailers(@Path("id") long id, @Query("api_key") String key);
}
