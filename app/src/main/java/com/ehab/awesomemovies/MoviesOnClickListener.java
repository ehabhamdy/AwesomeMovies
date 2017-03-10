package com.ehab.awesomemovies;

import com.ehab.awesomemovies.model.MovieDetail;

/**
 * Created by ehabhamdy on 3/11/17.
 */

public interface MoviesOnClickListener {
    void onListItemClick(MovieDetail clickedItemIndex);
}
