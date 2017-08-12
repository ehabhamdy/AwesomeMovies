package com.ehab.awesomemovies.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ehab.awesomemovies.MoviesOnClickListener;
import com.ehab.awesomemovies.NetworkUtilities.NetworkUtils;
import com.ehab.awesomemovies.data.PopularMoviesAdapter;
import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.model.MovieDetail;
import com.ehab.awesomemovies.ui.moviedetail.DetailsActivity;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.net.URL;
import java.util.List;

/**
 * Created by ehabhamdy on 2/15/17.
 */

public class MoviesFragment extends Fragment implements MoviesOnClickListener, MoviesContract.View, LoaderManager.LoaderCallbacks<MovieDetail[]>{

    public int MOVIES_LOADER_ID = 22;
    public static final String EXTRA_MOVIE_DETAILS = "movie-details";

    private MoviesContract.UserActionsListener mActionsListener;

    private static final String ARG_DATA = "data";

    RecyclerView mMoviesRecyclerview;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    PopularMoviesAdapter mAdapter;

    public static MoviesFragment newInstance(int type){
        Bundle args = new Bundle();
        //args.putSerializable(ARG_CRIME_ID, crimeId);
        args.putInt(ARG_DATA, type);
        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Here I am initializing the loader id based on the purpose of fragment
        // to be able to load different data from the server, thus creating multiple asyncTaskLoaders
        MOVIES_LOADER_ID = getArguments().getInt(ARG_DATA);
        mActionsListener = new MoviesPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movies, container, false);

        mMoviesRecyclerview = (RecyclerView) v.findViewById(R.id.movies_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        FlexboxLayoutManager flexLayoutManager = new FlexboxLayoutManager();
        flexLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexLayoutManager.setAlignItems(AlignItems.STRETCH);

        mMoviesRecyclerview.setLayoutManager(gridLayoutManager);
        mMoviesRecyclerview.setHasFixedSize(true);

        mAdapter = new PopularMoviesAdapter(getContext(), this);

        mMoviesRecyclerview.setAdapter(mAdapter);

        mLoadingIndicator = (ProgressBar) v.findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) v.findViewById(R.id.tv_error_message_display);

        int loaderId = MOVIES_LOADER_ID;

        LoaderManager.LoaderCallbacks<MovieDetail[]> callback = this;

        // We created bundle to pass extra data through initloader that can be accessed inside onCreateLoader
        Bundle bundleForLoader = null;
        getActivity().getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

        return v;
    }

    @Override
    public Loader<MovieDetail[]> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<MovieDetail[]>(getContext()) {
            MovieDetail[]  mMoviesDetails;

            @Override
            protected void onStartLoading() {
                if(args != null)
                    deliverResult(mMoviesDetails);
                else{
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public MovieDetail[] loadInBackground() {
                URL moviesRequestURL = NetworkUtils.buildUrl(MOVIES_LOADER_ID);
                Log.d("Activityyyyy", moviesRequestURL.toString());

                try {
                    String jsonMoviesResponse = NetworkUtils
                            .getResponseFromHttpUrl(moviesRequestURL);

                    MovieDetail[] allMovies = NetworkUtils
                            .getMoviesDetailsFromJson((MainActivity) getActivity(), jsonMoviesResponse);

                    return allMovies;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }

            @Override
            public void deliverResult(MovieDetail[] data) {
                mMoviesDetails = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MovieDetail[]> loader, MovieDetail[] data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.setNewData(data);
        if(data == null)
            showErrorMessage();
        else
            showMovieDataView();
    }

    @Override
    public void onLoaderReset(Loader<MovieDetail[]> loader) {

    }


    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mMoviesRecyclerview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mMoviesRecyclerview.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(MovieDetail movie) {
        mActionsListener.openMovieDetails(movie);
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showMovies(List<MovieDetail> notes) {

    }

    @Override
    public void showAddMovie() {

    }

    @Override
    public void showMovieDetailUi(MovieDetail movie) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_DETAILS, movie.getId());
        startActivity(intent);
    }

}
