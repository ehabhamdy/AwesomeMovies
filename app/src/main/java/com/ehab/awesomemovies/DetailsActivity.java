package com.ehab.awesomemovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ehab.awesomemovies.model.MovieDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ehab.awesomemovies.MainActivity.EXTRA_MOVIE_DETAILS;


public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.movie_title)
    TextView title;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.activity_details)
    RelativeLayout detailsInfo;
    @BindView(R.id.backdrop_imageview)
    ImageView bdImageView;
    @BindView(R.id.poster_imageview)
    ImageView posterImageView;
    @BindView(R.id.date_textview)
    TextView dateTextView;
    @BindView(R.id.rating_textview)
    TextView ratingTextView;
    @BindView(R.id.overview_textview)
    TextView overviewTextView;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final int movieId = intent.getIntExtra(EXTRA_MOVIE_DETAILS, 2);

        title.setText(String.valueOf(movieId));

        new FetchDetailsTask() {
            @Override
            protected void onPreExecute() {
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(MovieDetail movieDetail) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (movieDetail != null) {
                    showMovieDataView();

                    Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/"+movieDetail.getBackdropPath()).into(bdImageView);
                    Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w342/"+movieDetail.getPosterPath()).into(posterImageView);

                    title.setText(movieDetail.getTitle());
                    dateTextView.setText(movieDetail.getReleaseDate());
                    ratingTextView.setText(movieDetail.getVoteAverage().toString() + " / 10");
                    overviewTextView.setText(movieDetail.getOverview());

                }
                else{
                    showErrorMessage();
                }
            }
        }.execute(movieId);
    }

    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        detailsInfo.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        detailsInfo.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
