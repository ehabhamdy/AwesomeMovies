package com.ehab.awesomemovies;

import android.content.Context;
import android.os.AsyncTask;

import com.ehab.awesomemovies.NetworkUtilities.NetworkUtils;
import com.ehab.awesomemovies.model.Review;

import java.net.URL;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class FetchReviewsTask extends AsyncTask<Integer, Void, Review[]> {

    private Context mContext;
    public FetchReviewsTask(Context context){
        mContext = context;
    }

    @Override
    protected Review[] doInBackground(Integer... integers) {
        URL reviewsRequestURL = NetworkUtils.buildReviewsUri(integers[0]);
        try{
            String jsonReviewsResponse = NetworkUtils
                    .getResponseFromHttpUrl(reviewsRequestURL);

            Review[] allReviews = NetworkUtils.getReviewsDetailsFromJson(mContext, jsonReviewsResponse);
            return allReviews;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}