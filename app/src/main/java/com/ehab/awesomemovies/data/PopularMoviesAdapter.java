package com.ehab.awesomemovies.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ehab.awesomemovies.MoviesOnClickListener;
import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.model.MovieDetail;
import com.squareup.picasso.Picasso;


/**
 * Created by ehabhamdy on 2/13/17.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.MovieViewHolder> {




    final private MoviesOnClickListener mOnClickListener;

    private MovieDetail[] mMovies;
    private Context mContext;


    public PopularMoviesAdapter(Context context){
        mContext = context;
        mOnClickListener = (MoviesOnClickListener) context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentDirectly = false;

        View view = inflater.inflate(R.layout.movie_list_item, parent, shouldAttachToParentDirectly);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mMovies[position]);
    }

    @Override
    public int getItemCount() {
        if(mMovies == null)
            return 0;
        else
            return mMovies.length;
    }



    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView movieImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.movie_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            MovieDetail clickedMovie = mMovies[clickPosition];
            mOnClickListener.onListItemClick(clickedMovie);
        }

        public void bind(MovieDetail movie) {
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+movie.getPosterPath()).into(movieImageView);
        }
    }

    public void setNewData(MovieDetail[] movies){
        mMovies = movies;
        notifyDataSetChanged();
    }
}
