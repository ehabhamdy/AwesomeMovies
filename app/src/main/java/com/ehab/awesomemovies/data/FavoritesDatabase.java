package com.ehab.awesomemovies.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by ehabhamdy on 3/10/17.
 */

@Database(version = FavoritesDatabase.VERSION)
public class FavoritesDatabase {
    public static final int VERSION = 1;

    @Table(MoviesColumns.class) public static final String MOVIES = "movies";
}
