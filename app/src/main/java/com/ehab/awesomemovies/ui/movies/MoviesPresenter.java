package com.ehab.awesomemovies.ui.movies;

import android.support.annotation.NonNull;

import com.ehab.awesomemovies.model.MovieDetail;

import java.util.List;

/**
 * Created by ehabhamdy on 8/12/17.
 */

public class MoviesPresenter implements MoviesContract.UserActionsListener {

    private final MoviesContract.View mMoviesView;

    public MoviesPresenter(MoviesContract.View mNotesView) {
        this.mMoviesView = mNotesView;
    }

    @Override
    public void loadMovies(boolean forceUpdate) {

    }

    @Override
    public void openMovieDetails(@NonNull MovieDetail requestedMovie) {
        mMoviesView.showMovieDetailUi(requestedMovie);
    }
}
