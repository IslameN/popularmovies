package com.misla.popularmovies.view.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.misla.popularmovies.BuildConfig;
import com.misla.popularmovies.internet.MyRetrofit;
import com.misla.popularmovies.internet.model.Trailer;
import com.misla.popularmovies.internet.results.TrailerResults;
import com.misla.popularmovies.internet.services.TrailerDataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * View model for the trailers.
 */
public class TrailerViewModel extends ViewModel {
    /**
     * Tag for debugging purposes.
     */
    private static final String TAG = TrailerViewModel.class.getSimpleName();

    /**
     * List of trailer.
     */
    private MutableLiveData<List<Trailer>> trailers;

    /**
     * Gets the trailers of a movie.
     * @param id Id of the movie.
     * @return Trailers of the movie.
     */
    public LiveData<List<Trailer>> getTrailers(int id) {
        if (trailers == null) {
            trailers = new MutableLiveData<>();

            loadTrailers(id);
        }
        return trailers;
    }

    /**
     * Load the trailers.
     * @param id Id of the movie.
     */
    private void loadTrailers(int id) {
        TrailerDataService trailerDbHelper = MyRetrofit.getRetrofitInstance().create(TrailerDataService.class);
        Call<TrailerResults> call = trailerDbHelper.getTrailers(id, BuildConfig.APIKEY);
        call.enqueue(new retrofit2.Callback<TrailerResults>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResults> call, @NonNull Response<TrailerResults> response) {
                if (response.code() == 200) {
                    trailers.setValue(response.body().getTrailerResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResults> call, @NonNull Throwable t) {
                Log.e(TAG, "An error occurred: " + t.toString());
            }
        });
    }
}
