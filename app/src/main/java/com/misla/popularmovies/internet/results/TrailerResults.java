package com.misla.popularmovies.internet.results;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.misla.popularmovies.internet.model.Trailer;

import java.util.ArrayList;

/**
 * Helper class that creates trailer results.
 */
public class TrailerResults implements Parcelable {

    /**
     * Id.
     */
    @SerializedName("id")
    private int id;

    /**
     * Id.
     */
    @SerializedName("results")
    private ArrayList<Trailer> trailerResults;

    /**
     * Gets the id.
     * @return Id of the trailers.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id Id of the trailers.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the trailers.
     * @return Trailers of the selected movie.
     */
    public ArrayList<Trailer> getTrailerResults() {
        return trailerResults;
    }

    /**
     * Sets the trailers.
     * @param trailerResults Trailers of the selected movie.
     */
    public void setTrailerResults(ArrayList<Trailer> trailerResults) {
        this.trailerResults = trailerResults;
    }

    /**
     * Parcel constuctor.
     * @param parcel parcel to be recreated.
     */
    TrailerResults(Parcel parcel) {
        id = parcel.readInt();
        parcel.readList(trailerResults, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeList(trailerResults);
    }

    /**
     * Creator for Parcelable implementation.
     */
    public static final Parcelable.Creator<TrailerResults> CREATOR = new Parcelable.Creator<TrailerResults>() {
        public TrailerResults createFromParcel(Parcel in) {
            return new TrailerResults(in);
        }

        public TrailerResults[] newArray(int size) {
            return new TrailerResults[size];
        }
    };
}
