package com.ehab.awesomemovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ehabhamdy on 3/12/17.
 */

public class MoviesContract {
    public static final String CONTENT_AUTHORITY = "com.ehab.awesomemovies.data.FavoritesProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITE = "favorite";

    public static final class FavoritesEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE)
                .build();

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static Uri buildMovieUriWithID(String id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }
    }
}
