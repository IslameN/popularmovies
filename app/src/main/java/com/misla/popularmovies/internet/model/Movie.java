package com.misla.popularmovies.internet.model;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class that represents a movie with all the information downloaded from
 * https://www.themoviedb.org. It is organized in the same way it is written in the json.
 */
@Entity(tableName = "favourite_movies")
public class Movie implements Parcelable {

    /**
     * Image loading base url.
     */
    private static final String IMAGES_BASE_URL = "http://image.tmdb.org/t/p/";

    /**
     * Default width of the images.
     */
    private static final String WIDTH = "w500";

    /**
     * Vote count.
     */
    @SerializedName("vote_count")
    private int voteCount;

    /**
     * Identifier.
     */
    @PrimaryKey
    @SerializedName("id")
    private int id;

    /**
     * Video available indicator.
     */
    @SerializedName("video")
    private boolean video;

    /**
     * Average vote. 0 to 10.
     */
    @SerializedName("vote_average")
    private double voteAverage;

    /**
     * Title.
     */
    @SerializedName("title")
    private String title;

    /**
     * Popularity.
     */
    @SerializedName("popularity")
    private double popularity;

    /**
     * Path to the poster,
     */
    @SerializedName("poster_path")
    private String posterPath;

    /**
     * Original language.
     */
    @SerializedName("original_language")
    private String originalLanguage;

    /**
     * Original title.
     */
    @SerializedName("original_title")
    private String originalTitle;

    /**
     * Ids of the related genres.
     */
    @SerializedName("genre_ids")
    private List<Integer> genreId;

    /**
     * Path of the backdrop image.
     */
    @SerializedName("backdrop_path")
    private String backdropPath;

    /**
     * Adult movie indicator.
     */
    @SerializedName("adult")
    private boolean adult;

    /**
     * Overview.
     */
    @SerializedName("overview")
    private String overview;

    /**
     * Release date.
     */
    @SerializedName("release_date")
    private String releaseDate;


    // Getters and setters
    /**
     * Gets the vote count.
     * @return vote count of the movie.
     */
    public int getVoteCount() {
        return voteCount;
    }

    /**
     * Sets the vote count.
     * @param voteCount vote count of the movie.
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * Gets the id.
     * @return id of the movie.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id id of the movie.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if video is available.
     * @return True if video is available, false otherwise.
     */
    public boolean isVideo() {
        return video;
    }

    /**
     * Sets if video is available.
     * @param video True if video is available, false otherwise.
     */
    public void setVideo(boolean video) {
        this.video = video;
    }

    /**
     * Gets the vote average.
     * @return vote average of the movie.
     */
    public double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Sets the vote average.
     * @param voteAverage vote average of the movie.
     */
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * Gets the tile.
     * @return Title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * @param title Title of the movie.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the popularity.
     * @return popularity of the movie.
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * Sets the popularity.
     * @param popularity popularity of the movie.
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * Gets the poster path.
     * @return poster path of the movie.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Sets the poster path.
     * @param posterPath poster path of the movie.
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Gets full poster path.
     * @return Full poster path, including movie db url.
     */
    public String getFullPosterPath() {
        return IMAGES_BASE_URL + WIDTH + posterPath;
    }

    /**
     * Gets the original language.
     * @return original language of the movie.
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * Sets the original language.
     * @param originalLanguage original language of the movie.
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * Gets the original title.
     * @return original title of the movie.
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Sets the original title.
     * @param originalTitle original title of the movie.
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Gets the gender identification.
     * @return gender identification of the movie.
     */
    public List<Integer> getGenreId() {
        return genreId;
    }

    /**
     * Sets the gender identification.
     * @param genreId gender identification of the movie.
     */
    public void setGenreId(List<Integer> genreId) {
        this.genreId = genreId;
    }

    /**
     * Gets the backdrop path.
     * @return backdrop path of the movie.
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Gets full backdrop path.
     * @return Full backdrop path, including movie db url.
     */
    public String getFullBackdropPath() {
        return IMAGES_BASE_URL + WIDTH + backdropPath;
    }

    /**
     * Sets the backdrop path.
     * @param backdropPath backdrop path of the movie.
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * Checks whether is an adult movie or not.
     * @return true if the movie is for adults, false otherwise.
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * Sets the adult rating.
     * @param adult true if the movie is for adults, false otherwise.
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * Gets the overview.
     * @return overview of the movie.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets the overview.
     * @param overview overview of the movie.
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Gets the release date.
     * @return release date of teh movie.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date.
     * @param releaseDate release date of the movie.
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Constructor for database
    public Movie() {

    }

    // To string implementation.
    @NonNull
    @Override
    public String toString() {
        return "Movie -> Title: " + title + " , poster path: " + posterPath
                + ", vote count: " + voteCount + ", id: " + id + " video: " + video
                + ", vote average: " + voteAverage + ", popularity: " + popularity
                + ", original language: " + originalLanguage + ", original title:" + originalTitle
                + ", backdrop path: " + backdropPath + ", adult: " + adult
                + ", overview: " + overview + ", release date: " + releaseDate
                + ", genres: " + genreId.toString() + ".";
    }

    // Parcelable implementation.
    /**
     * Parcelable constructor of the class.
     * @param parcel Parcel to be recreated.
     */
    Movie(Parcel parcel) {
        voteCount = parcel.readInt();
        id = parcel.readInt();
        video = parcel.readByte() != 0;
        voteAverage = parcel.readDouble();
        title = parcel.readString();
        popularity = parcel.readDouble();
        posterPath = parcel.readString();
        originalLanguage = parcel.readString();
        originalTitle = parcel.readString();
        backdropPath = parcel.readString();
        adult = parcel.readByte() != 0;
        overview = parcel.readString();
        releaseDate = parcel.readString();
        genreId = parcel.readArrayList(Movie.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteCount);
        dest.writeInt(id);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(backdropPath);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeList(genreId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Creator for Parcelable implementation.
     */
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
