package com.misla.popularmovies.internet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that represents a Retrofit instance for internet queries.
 */
public class MyRetrofit {
    /**
     * Base url to make queries to the movie database.
     */
    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    /**
     * Retrofit instance for internet communication.
     */
    private static Retrofit retrofit;

    // TODO: check @singleton annotation
    /**
     * Retrofit instance for internet communication.
     * @return Retrofit instance.
     */
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
