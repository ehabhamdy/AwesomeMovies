package com.ehab.awesomemovies.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.model.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    public interface ListItemClickListener{
        void onListItemClick(Trailer clickedItemIndex);
    }

    final private ListItemClickListener mOnClickListener;

    Trailer[] mTrailers;
    Context mContext;

    public TrailersAdapter(Context context){
        mContext = context;
        mOnClickListener = (ListItemClickListener) context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentDirectly = false;

        View view = inflater.inflate(R.layout.trailer_list_item, parent, shouldAttachToParentDirectly);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(mTrailers[position]);

    }

    @Override
    public int getItemCount() {
        if(mTrailers == null)
            return 0;
        else
            return mTrailers.length;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView trailerImageView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerImageView = (ImageView) itemView.findViewById(R.id.trailer_item_image);
            itemView.setOnClickListener(this);
        }

        public void bind(Trailer trailer) {
            Log.d("asdfsdafdsfsdf", "http://img.youtube.com/vi/"+trailer.getSource()+"/mqdefault.jpg");
            Picasso.with(mContext).load("http://img.youtube.com/vi/"+trailer.getSource()+"/mqdefault.jpg").into(trailerImageView);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            Trailer clickedTrailer = mTrailers[clickPosition];
            mOnClickListener.onListItemClick(clickedTrailer);
        }
    }

    public void setNewData(Trailer[] trailers){
        mTrailers = trailers;
        notifyDataSetChanged();
    }
}
