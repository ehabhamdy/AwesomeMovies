package com.ehab.awesomemovies.ui.Favorites;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehab.awesomemovies.MoviesOnClickListener;
import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.data.FavoritesAdapter;
import com.ehab.awesomemovies.data.MoviesContract;
import com.ehab.awesomemovies.model.MovieDetail;
import com.ehab.awesomemovies.ui.moviedetail.DetailsActivity;
import com.ehab.awesomemovies.ui.movies.MoviesContractMVP;
import com.ehab.awesomemovies.ui.movies.MoviesPresenter;

import java.util.List;

import static com.ehab.awesomemovies.ui.movies.MoviesFragment.EXTRA_MOVIE_DETAILS;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class FavoritesFragment extends Fragment  implements MoviesOnClickListener, LoaderManager.LoaderCallbacks<Cursor>, MoviesContractMVP.View{
    public int MOVIES_LOADER_ID = 4;

    private static final String ARG_DATA = "data";

    private RecyclerView mFavoritesRecyclerview;
    FavoritesAdapter mAdapter;

    private MoviesContractMVP.UserActionsListener mActionsListener;

    public static FavoritesFragment newInstance(int type){
        Bundle args = new Bundle();
        //args.putSerializable(ARG_CRIME_ID, crimeId);
        args.putInt(ARG_DATA, type);
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionsListener = new MoviesPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        mFavoritesRecyclerview = (RecyclerView) v.findViewById(R.id.movies_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mFavoritesRecyclerview.setLayoutManager(gridLayoutManager);
        mFavoritesRecyclerview.setHasFixedSize(true);

        mAdapter = new FavoritesAdapter(getContext(), this);
        mFavoritesRecyclerview.setAdapter(mAdapter);

        int loaderId = MOVIES_LOADER_ID;
        LoaderManager.LoaderCallbacks<Cursor> callback = this;

        getActivity().getSupportLoaderManager().initLoader(loaderId, null, callback);

        return v;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new CursorLoader(getActivity(), MoviesContract.FavoritesEntry.CONTENT_URI, null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
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

    @Override
    public void onListItemClick(MovieDetail clickedItemIndex) {
        mActionsListener.openMovieDetails(clickedItemIndex);
    }
}
