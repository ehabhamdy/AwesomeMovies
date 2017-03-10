package com.ehab.awesomemovies.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.model.Review;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>{

    Review[] mReviews;
    Context mContext;

    public ReviewsAdapter(Context context){
        mContext = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentDirectly = false;

        View view = inflater.inflate(R.layout.review_list_item, parent, shouldAttachToParentDirectly);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(mReviews[position]);

    }

    @Override
    public int getItemCount() {
        if(mReviews == null)
            return 0;
        else
            return mReviews.length;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView authorTextView;
        TextView reviewTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            authorTextView = (TextView) itemView.findViewById(R.id.author_text_view);
            reviewTextView = (TextView) itemView.findViewById(R.id.review_text_view);
        }

        public void bind(Review review) {
            authorTextView.setText("Author: " + review.getAuthor());
            reviewTextView.setText( review.getContent());
        }

    }

    public void setNewData(Review[] reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }

}
