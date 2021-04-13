package com.misla.popularmovies.internet.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Class that represents a trailer of the movie.
 */
public class Trailer implements Parcelable {

    /**
     * Identifier.
     */
    @SerializedName("id")
    private String id;

    /**
     * ISO 369 1.
     */
    @SerializedName("iso_639_1")
    private String iso639;

    /**
     * ISO 3166 1.
     */
    @SerializedName("iso_3166_1")
    private String iso3166;

    /**
     * Key.
     */
    @SerializedName("key")
    private String key;

    /**
     * Name.
     */
    @SerializedName("name")
    private String name;

    /**
     * Site.
     */
    @SerializedName("site")
    private String site;

    /**
     * Size.
     */
    @SerializedName("size")
    private int size;

    /**
     * Type.
     */
    @SerializedName("type")
    private String type;

    /**
     * Gets the id.
     * @return Id of the trailer.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id Id of the trailer.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the iso 639.
     * @return Iso 630 of the trailer.
     */
    public String getIso639() {
        return iso639;
    }

    /**
     * Sets the iso 639.
     * @param iso639 Iso 639 of the trailer.
     */
    public void setIso639(String iso639) {
        this.iso639 = iso639;
    }

    /**
     * Gets the iso 3166.
     * @return Iso 3166 of the trailer.
     */
    public String getIso3166() {
        return iso3166;
    }

    /**
     * Sets the iso 3166.
     * @param iso3166 Iso 3166 of the trailer.
     */
    public void setIso3166(String iso3166) {
        this.iso3166 = iso3166;
    }

    /**
     * Gets the key.
     * @return Key of the trailer.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     * @param key Key of the trailer.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the name.
     * @return Name of the trailer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name Name of the trailer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the site.
     * @return Site of the trailer.
     */
    public String getSite() {
        return site;
    }

    /**
     * Sets the site SIte of the trailer..
     * @param site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * Gets the size.
     * @return Size of the trailer.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size.
     * @param size Size of the trailer.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the type.
     * @return Type of the trailer.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * @param type Type of the trailer.
     */
    public void setType(String type) {
        this.type = type;
    }

    // Parcelable implementation.
    /**
     * Parcelable constructor of the class.
     * @param parcel Parcel to be recreated.
     */
    Trailer(Parcel parcel) {
        id = parcel.readString();
        iso639 = parcel.readString();
        iso3166 = parcel.readString();
        key = parcel.readString();
        name = parcel.readString();
        site = parcel.readString();
        size = parcel.readInt();
        type = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iso639);
        dest.writeString(iso3166);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Creator for Parcelable implementation.
     */
    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    // To string.
    @Override
    public String toString() {
        return "Trailer -> id: " + id +
                ", iso 639 1: " + iso639 +
                ", iso 3166 1: " + iso3166 +
                ", key: " + key +
                ", name: " + name +
                ", site: " + site +
                ", size: " + size +
                ", type: " + type +
                ".";
    }
}
