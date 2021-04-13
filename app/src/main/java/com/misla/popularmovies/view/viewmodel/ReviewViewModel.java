package com.misla.popularmovies.view.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.misla.popularmovies.BuildConfig;
import com.misla.popularmovies.internet.MyRetrofit;
import com.misla.popularmovies.internet.model.Review;
import com.misla.popularmovies.internet.results.ReviewResults;
import com.misla.popularmovies.internet.services.ReviewDataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * View model for the reviews.
 */
public class ReviewViewModel extends ViewModel {
    /**
     * Tag for debugging purposes.
     */
    private static final String TAG = ReviewViewModel.class.getSimpleName();

    /**
     * List of review.
     */
    private MutableLiveData<List<Review>> reviews;

    /**
     * Gets the reviews of a movie.
     * @param id Id of the movie.
     * @return Reviews of the movie.
     */
    public LiveData<List<Review>> getReviews(int id) {
        if (reviews == null) {
            reviews = new MutableLiveData<>();

            loadReviews(id);
        }
        return reviews;
    }

    /**
     * Loads the review of the movie.
     * @param id Id of the movie.
     */
    private void loadReviews(int id) {
        ReviewDataService reviewDataService = MyRetrofit.getRetrofitInstance().create(ReviewDataService.class);
        Call<ReviewResults> call = reviewDataService.getReviews(id, BuildConfig.APIKEY);
        call.enqueue(new retrofit2.Callback<ReviewResults>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResults> call, @NonNull Response<ReviewResults> response) {
                if (response.code() == 200) {
                    reviews.setValue(response.body().getReviewResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResults> call, @NonNull Throwable t) {
                Log.e(TAG, "ERROR: " + t.toString());
            }
        });
    }
}
