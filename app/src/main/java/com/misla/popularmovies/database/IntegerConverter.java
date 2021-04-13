package com.misla.popularmovies.database;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that converts a list of Integer to a String in order to save their values into Room
 * database and vice versa.
 */
public class IntegerConverter {
    /**
     * Converts a list of Integer into a String to be saved in Room's databases.
     * @param list List of Integer.
     * @return String value to be stored.
     */
    @TypeConverter
    public static String toString(List<Integer> list) {
        StringBuilder result = new StringBuilder();
        for (Integer integer: list) {
            result.append(integer);
            result.append(" ");
        }
        return result.toString();
    }

    /**
     * Converts a String from a Room database to a list of Integer.
     * @param genres Genres to be converted to List<Integer>.
     * @return List of Integers with the genres.
     */
    @TypeConverter
    public static List<Integer> toIntegerList(String genres) {
        List<Integer> result = new ArrayList<>();
        for (String genre: genres.split(" ")) {
            result.add(Integer.valueOf(genre));
        }
        return result;
    }
}
