package com.ehab.awesomemovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ehab.awesomemovies.data.MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID;
import static com.ehab.awesomemovies.data.MoviesContract.FavoritesEntry.COLUMN_POSTER_PATH;
import static com.ehab.awesomemovies.data.MoviesContract.FavoritesEntry.COLUMN_TITLE;
import static com.ehab.awesomemovies.data.MoviesContract.FavoritesEntry.TABLE_NAME;

/**
 * Created by ehabhamdy on 3/12/17.
 */

public class FavoritesSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public FavoritesSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE " + TABLE_NAME + "(");
        sql.append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sql.append(COLUMN_MOVIE_ID + " VARCHAR NOT NULL, ");
        sql.append(COLUMN_TITLE + " VARCHAR NOT NULL, ");
        sql.append(COLUMN_POSTER_PATH + " VARCHAR NOT NULL");
        sql.append(")");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
