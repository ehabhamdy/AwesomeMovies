package com.ehab.awesomemovies.data;

import android.provider.BaseColumns;

/**
 * Created by ehabhamdy on 3/9/17.
 */

public class MoviesContract {

    public class MoviesEntry implements BaseColumns {
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_GENRE_ID = "genre_id";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String TABLE_NAME = "movies";
        public static final String DATABASE_NAME = "favorite_movies.db";
        public static final int DATABASE_VERSION = 1;

    }
}
