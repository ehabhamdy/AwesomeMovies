package com.ehab.awesomemovies.ui.fragments;

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

import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.data.FavoritesAdapter;
import com.ehab.awesomemovies.data.MoviesContract;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class FavoritesFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{
    public int MOVIES_LOADER_ID = 4;

    private static final String ARG_DATA = "data";

    private RecyclerView mFavoritesRecyclerview;
    FavoritesAdapter mAdapter;


    public static FavoritesFragment newInstance(int type){
        Bundle args = new Bundle();
        //args.putSerializable(ARG_CRIME_ID, crimeId);
        args.putInt(ARG_DATA, type);
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        mFavoritesRecyclerview = (RecyclerView) v.findViewById(R.id.movies_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mFavoritesRecyclerview.setLayoutManager(gridLayoutManager);
        mFavoritesRecyclerview.setHasFixedSize(true);

        mAdapter = new FavoritesAdapter(getContext());
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


}
