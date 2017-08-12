package com.ehab.awesomemovies.ui.movies;

import android.support.annotation.NonNull;

import com.ehab.awesomemovies.model.MovieDetail;

import java.util.List;

/**
 * Created by ehabhamdy on 8/12/17.
 */

public interface MoviesContract {
    interface View {

        void setProgressIndicator(boolean active);

        void showMovies(List<MovieDetail> notes);

        void showAddMovie();

        void showMovieDetailUi(MovieDetail movie);
    }

    interface UserActionsListener {

        void loadMovies(boolean forceUpdate);

        void openMovieDetails(@NonNull MovieDetail requestedNote);
    }
}
