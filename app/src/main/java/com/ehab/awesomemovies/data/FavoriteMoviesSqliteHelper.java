package com.ehab.awesomemovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_ADULT;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_GENRE_ID;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_MOVIE_ID;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_OVERVIEW;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_TITLE;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.DATABASE_NAME;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.DATABASE_VERSION;
import static com.ehab.awesomemovies.data.MoviesContract.MoviesEntry.TABLE_NAME;

/**
 * Created by ehabhamdy on 3/9/17.
 */

public class FavoriteMoviesSqliteHelper extends SQLiteOpenHelper {

    public FavoriteMoviesSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE " + TABLE_NAME + "(");
        sql.append(_ID + " INTEGER PRIMARY KEY AUTO INCREMET, ");
        sql.append(COLUMN_MOVIE_ID + " INTEGER, ");
        sql.append(COLUMN_TITLE + " VARCHAR NOT NULL, ");
        sql.append(COLUMN_BACKDROP_PATH + " VARCHAR NOT NULL, ");
        sql.append(COLUMN_ADULT + " INTEGER NOT NULL, ");
        sql.append(COLUMN_RELEASE_DATE + " VARCHAR NOT NULL, ");
        sql.append(COLUMN_VOTE_AVERAGE + " REAL NOT NULL, ");
        sql.append(COLUMN_GENRE_ID + " INTEGER NOT NULL, ");
        sql.append(COLUMN_OVERVIEW + " VARCHAR NOT NULL");
        sql.append(")");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(sqLiteDatabase);
    }
}
