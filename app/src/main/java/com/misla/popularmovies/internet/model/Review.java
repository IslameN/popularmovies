package com.misla.popularmovies.internet.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Class that creates a review of the movie.
 */
public class Review implements Parcelable {
    /**
     * Author.
     */
    @SerializedName("author")
    private String author;

    /**
     * Content.
     */
    @SerializedName("content")
    private String content;

    /**
     * Identifier.
     */
    @SerializedName("id")
    private String id;

    /**
     * Url.
     */
    @SerializedName("url")
    private String url;

    /**
     * Gets the author.
     * @return Author of the review.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     * @param author Author of the review.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the content.
     * @return Content of the review.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     * @param content Content of the review.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the id.
     * @return Id of the review.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id Id of the review.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the url.
     * @return Url of the review.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url of the review.
     * @param url Url of the review.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    // Parecel implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(id);
        dest.writeString(url);
    }

    public Review(Parcel in) {
        author = in.readString();
        content = in.readString();
        id = in.readString();
        url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    // To string
    @NonNull
    @Override
    public String toString() {
        return "Review -> Author: " + author +
                ", content: " + content +
                ", id: " + id +
                ", url: " + url +
                ".";
    }
}
