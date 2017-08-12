package com.ehab.awesomemovies.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ehab.awesomemovies.MoviesOnClickListener;
import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.model.MovieDetail;
import com.ehab.awesomemovies.ui.Favorites.FavoritesFragment;
import com.squareup.picasso.Picasso;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private final Context mContext;

    final private MoviesOnClickListener mClickHandler;


    private Cursor mCursor;

    public FavoritesAdapter(@NonNull Context context, FavoritesFragment f) {
        mContext = context;
        mClickHandler = (MoviesOnClickListener) f;
    }
    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentDirectly = false;

        View view = inflater.inflate(R.layout.movie_list_item, parent, shouldAttachToParentDirectly);
        FavoritesViewHolder viewHolder = new FavoritesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String poster = mCursor.getString(mCursor.getColumnIndex("poster_path"));


        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+poster).into(holder.posterImageView);


    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView posterImageView;
        public FavoritesViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.movie_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            String movieId = mCursor.getString(mCursor.getColumnIndex("movie_id"));
            MovieDetail d = new MovieDetail();
            d.setId(Integer.valueOf(movieId));
            mClickHandler.onListItemClick(d);
        }
    }
}
