package com.misla.popularmovies.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.misla.popularmovies.R;

/**
 * Activity for displaying the grid of available movies.
 */
public class MovieGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);
    }
}
