package com.ehab.awesomemovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.ehab.awesomemovies.NetworkUtilities.NetworkUtils;
import com.ehab.awesomemovies.model.Trailer;

import java.net.URL;

/**
 * Created by ehabhamdy on 3/10/17.
 */

public class FetchTralersTask extends AsyncTask<Integer, Void, Trailer[]> {

    private Context mContext;
    public FetchTralersTask(Context context){
        mContext = context;
    }

    @Override
    protected Trailer[] doInBackground(Integer... strings) {
        URL trailersRequestURL = NetworkUtils.buildTrailersUri(strings[0]);
        try{
            String jsonTrailersResponse = NetworkUtils
                    .getResponseFromHttpUrl(trailersRequestURL);

            Trailer[] allTrailers = NetworkUtils.getTrailersDetailsFromJson(mContext, jsonTrailersResponse);
            return allTrailers;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
