package com.misla.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.misla.popularmovies.internet.model.Movie;

/**
 * Class for working with Room database.
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters(IntegerConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Singleton of the class.
     */
    private static AppDatabase appDatabase;

    /**
     * Lock for the singleton.
     */
    private static final Object LOCK = new Object();

    /**
     * Name of the database.
     */
    private static final String DATABASE = "PopularMoviesDatabase";

    /**
     * Gets singleton instance of the database.
     * @param context Context needed for Room initialization.
     * @return singleton of Room.
     */
    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            synchronized (LOCK) {
                appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE)
                        .build();
            }
        }
        return appDatabase;
    }

    /**
     * Dao of the Movie
     * @return dao of the movie.
     */
    public abstract MovieDao movieDao();
}
