package com.misla.popularmovies.database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class that performs actions in a thread that is not a the main thread.
 * Perfect for database insertions and deletions.
 */
public class MovieDatabaseExecutor {
    /**
     * Lock for singleton creation.
     */
    private static final Object LOCK = new Object();

    /**
     * Singleton instance of the class.
     */
    private static MovieDatabaseExecutor movieDatabaseExecutor;

    /**
     * Executor of the class.
     */
    private final Executor diskIO;

    /**
     * Private constuctor.
     * @param diskIO Executor created.
     */
    private MovieDatabaseExecutor(Executor diskIO) {
        this.diskIO = diskIO;
    }

    /**
     * Singleton instance of the class.
     * @return Singleton instance of the class.
     */
    public static MovieDatabaseExecutor getInstance() {
        if (movieDatabaseExecutor == null) {
            synchronized (LOCK) {
                movieDatabaseExecutor = new MovieDatabaseExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return movieDatabaseExecutor;
    }

    /**
     * Executor to be executed.
     * @return Executor to be executed.
     */
    public Executor diskIO() {
        return diskIO;
    }
}