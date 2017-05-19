package com.ehab.awesomemovies.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.ehab.awesomemovies.NetworkUtilities.NetworkUtils;
import com.ehab.awesomemovies.model.MovieDetail;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ehabhamdy on 2/15/17.
 */

public class FetchDetailsTask extends AsyncTask<Integer, Void, MovieDetail>{

    @Override
    protected MovieDetail doInBackground(Integer... inputs) {
        int id = inputs[0];

        try {
            URL movieDetailsRequestURL = NetworkUtils.buildUrl(id);

            String jsonMovieDetailResponse = NetworkUtils
                    .getResponseFromHttpUrl_details(movieDetailsRequestURL);

            MovieDetail details = NetworkUtils
                    .getSingleMovieDetailsFromJson(jsonMovieDetailResponse);

            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://image.tmdb.org/t/p/w500/"+details.getBackdropPath()).getContent());

            details.setBitmap(bitmap);
            return details;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
