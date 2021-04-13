package com.misla.popularmovies.internet.results;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.misla.popularmovies.internet.model.Movie;

import java.util.ArrayList;

/**
 * Helper class to load movie database from themoviedb.org.
 */
public class MovieResults implements Parcelable {

    /**
     * Number of page.
     */
    @SerializedName("page")
    private int page;

    /**
     * Number of results.
     */
    @SerializedName("total_results")
    private int totalResults;

    /**
     * Total number of pages.
     */
    @SerializedName("total_pages")
    private int totalPages;

    /**
     * List of movies queried.
     */
    @SerializedName("results")
    private ArrayList<Movie> movieResult;

    /**
     * Parcel constuctor.
     * @param parcel parcel to be recreated.
     */
    MovieResults(Parcel parcel) {
        page = parcel.readInt();
        totalResults = parcel.readInt();
        totalPages = parcel.readInt();
        parcel.readList(movieResult, null);
    }

    /**
     * Gets the page selected.
     * @return page selected.
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the page selected.
     * @param page page selected.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets the total results.
     * @return total results queried.
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * Sets the total results queried.
     * @param totalResults total results queried.
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * Gets the total pages.
     * @return total queries queried.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the total pages.
     * @param totalPages total pages queried.
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Gets the list of movies.
     * @return list of movies queried.
     */
    public ArrayList<Movie> getMovieResult() {
        return movieResult;
    }

    /**
     * Sets the list of movies.
     * @param movieResult list of movies queried.
     */
    public void setMovieResult(ArrayList<Movie> movieResult) {
        this.movieResult = movieResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(totalPages);
        dest.writeInt(totalResults);
        dest.writeList(movieResult);
    }

    /**
     * Creator for Parcelable implementation.
     */
    public static final Parcelable.Creator<MovieResults> CREATOR = new Parcelable.Creator<MovieResults>() {
        public MovieResults createFromParcel(Parcel in) {
            return new MovieResults(in);
        }

        public MovieResults[] newArray(int size) {
            return new MovieResults[size];
        }
    };
}
