package com.misla.popularmovies.internet.results;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.misla.popularmovies.internet.model.Review;

import java.util.ArrayList;

/**
 * Helper class to parse reviews.
 */
public class ReviewResults implements Parcelable {

    /**
     * Id.
     */
    @SerializedName("id")
    private int id;

    /**
     * Page.
     */
    @SerializedName("page")
    private int page;

    /**
     * Id.
     */
    @SerializedName("results")
    private ArrayList<Review> reviewResults;

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
     * Gets the id.
     * @return Id of the reviews.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id Id of the reviews.
     */
    public void setId(int id) {
        this.id = id;
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
     * Gets the reviews.
     * @return Reviews of the selected movie.
     */
    public ArrayList<Review> getReviewResults() {
        return reviewResults;
    }

    /**
     * Sets the reviews.
     * @param reviewResults Reviews of the selected movie.
     */
    public void setReviewResults(ArrayList<Review> reviewResults) {
        this.reviewResults = reviewResults;
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
     * Parcel constuctor.
     * @param parcel parcel to be recreated.
     */
    ReviewResults(Parcel parcel) {
        id = parcel.readInt();
        page = parcel.readInt();
        parcel.readList(reviewResults, null);
        totalPages = parcel.readInt();
        totalResults = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(page);
        dest.writeList(reviewResults);
        dest.writeInt(totalPages);
        dest.writeInt(totalResults);
    }

    /**
     * Creator for Parcelable implementation.
     */
    public static final Parcelable.Creator<ReviewResults> CREATOR = new Parcelable.Creator<ReviewResults>() {
        public ReviewResults createFromParcel(Parcel in) {
            return new ReviewResults(in);
        }

        public ReviewResults[] newArray(int size) {
            return new ReviewResults[size];
        }
    };
}
