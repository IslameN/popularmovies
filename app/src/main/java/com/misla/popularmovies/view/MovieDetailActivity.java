package com.misla.popularmovies.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.misla.popularmovies.internet.model.Movie;
import com.misla.popularmovies.R;
import com.misla.popularmovies.database.AppDatabase;
import com.misla.popularmovies.database.MovieDatabaseExecutor;
import com.misla.popularmovies.view.viewmodel.MovieDetailActivityViewModel;
import com.misla.popularmovies.view.viewmodel.MovieDetailActivityViewModelFactory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

/**
 * Movie details activity.
 */
public class MovieDetailActivity extends AppCompatActivity {

    /**
     * Tag for debugging puporses.
     */
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    /**
     * Enum that handles the like button shown/not shown.
     */
    private enum ActionState {
        SHOWN,
        HIDDEN
    }

    /**
     * Action state of the floating button.
     */
    private ActionState actionState = ActionState.HIDDEN;

    /**
     * Floating action button for like/not like.
     */
    private FloatingActionButton floatingActionButton;

    /**
     * Selected movie.
     */
    private Movie movie;

    /**
     * Database.
     */
    private AppDatabase appDatabase;

    /**
     * Backdrop of the movie.
     */
    private ImageView backdrop;

    /**
     * True if teh movie is liked. False, otherwise.
     */
    private boolean liked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appDatabase = AppDatabase.getInstance(this);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(getString(R.string.movie_parcel));
        MovieDetailFragment.setMovie(movie);

        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        AppBarLayout appBarLayout = findViewById(R.id.main_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (floatingActionButton != null) {
                    if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                        if (actionState == ActionState.HIDDEN) {
                            actionState = ActionState.SHOWN;
                            invalidateOptionsMenu();
                        }
                    } else if (floatingActionButton.getVisibility() == View.GONE || floatingActionButton.getVisibility() == View.INVISIBLE) {
                        if (actionState == ActionState.HIDDEN) {
                            actionState = ActionState.SHOWN;
                            invalidateOptionsMenu();
                        }
                    } else {
                        if (actionState != ActionState.HIDDEN) {
                            actionState = ActionState.HIDDEN;
                            invalidateOptionsMenu();
                        }
                    }
                }
            }
        });

        setTitle(movie.getTitle());

        backdrop = findViewById(R.id.main_backdrop);

        if (savedInstanceState != null) {
            byte[] bytes = savedInstanceState.getByteArray(getString(R.string.bundle_image));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            backdrop.setImageBitmap(bitmap);
        } else {
            Picasso.get()
                    .load(movie.getFullBackdropPath())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(backdrop, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "An error occurred: " + e.toString());
                        }
                    });

            MovieDetailFragment fragment = new MovieDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liked) {
                    removeFavorite();
                } else {
                    addFavorite();
                }
            }
        });
        isFavorite();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bitmap bitmap = ((BitmapDrawable) backdrop.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        outState.putByteArray(getString(R.string.bundle_image), byteArrayOutputStream.toByteArray());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_movie_detail, menu);
        MenuItem item = menu.getItem(0);
        if (actionState == ActionState.SHOWN) {
            item.setVisible(true);
        } else if (actionState == ActionState.HIDDEN) {
            item.setVisible(false);
        }
        if (liked) {
            item.setIcon(R.drawable.liked);
        } else {
            item.setIcon(R.drawable.not_liked);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_like:
                if (liked) {
                    removeFavorite();
                } else {
                    addFavorite();
                }
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Add a movie to database.
     */
    private void addFavorite() {
        MovieDatabaseExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().insertFavoriteMovie(movie);
            }
        });
    }

    /**
     * Removes a favourite movie from database.
     */
    private void removeFavorite() {
        MovieDatabaseExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().deleteFavoriteMovie(movie);
            }
        });
    }

    /**
     * Check in the database if the movie is liked or not.
     */
    private void isFavorite() {
        MovieDetailActivityViewModelFactory factory = new MovieDetailActivityViewModelFactory(appDatabase, movie.getId());
        MovieDetailActivityViewModel viewModel =
                ViewModelProviders.of(MovieDetailActivity.this, factory).get(MovieDetailActivityViewModel.class);
        viewModel.getMovie().observe(MovieDetailActivity.this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie != null) {
                    liked = true;
                    showFavorite();
                } else {
                    liked = false;
                    showNotFavorite();
                }
            }
        });
    }

    /**
     * Called when the movie is liked.
     */
    private void showFavorite() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // I have to hide and show to show the button after hide/show from scrolling.
                floatingActionButton.setImageResource(R.drawable.liked);
                floatingActionButton.hide();
                floatingActionButton.show();
                invalidateOptionsMenu();
            }
        });
    }

    /**
     * Called when the movies is not liked.
     */
    private void showNotFavorite() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // I have to hide and show to show the button after hide/show from scrolling.
                floatingActionButton.setImageResource(R.drawable.not_liked);
                floatingActionButton.hide();
                floatingActionButton.show();
                invalidateOptionsMenu();
            }
        });
    }
}
