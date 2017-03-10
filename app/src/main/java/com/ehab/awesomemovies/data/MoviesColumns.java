package com.ehab.awesomemovies.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public interface MoviesColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String MOVIE_ID = "movie_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String POSTER_PATH = "poster_path";

    @DataType(DataType.Type.TEXT) @NotNull
    String TITLE = "title";
}
