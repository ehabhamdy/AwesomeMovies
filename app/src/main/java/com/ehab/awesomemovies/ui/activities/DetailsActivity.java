package com.ehab.awesomemovies.ui.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.data.MoviesContract;
import com.ehab.awesomemovies.data.ReviewsAdapter;
import com.ehab.awesomemovies.data.TrailersAdapter;
import com.ehab.awesomemovies.model.MovieDetail;
import com.ehab.awesomemovies.model.Review;
import com.ehab.awesomemovies.model.Trailer;
import com.ehab.awesomemovies.tasks.FetchDetailsTask;
import com.ehab.awesomemovies.tasks.FetchReviewsTask;
import com.ehab.awesomemovies.tasks.FetchTralersTask;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity implements TrailersAdapter.ListItemClickListener {

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

    private MovieDetail mMDetails;

    private TrailersAdapter mTrailersAdapter;
    private RecyclerView mTrailersRecyclerview;

    private ReviewsAdapter mReviewsAdapter;
    private RecyclerView mReviewsRecyclerview;

    private SparkButton mMakeFavoriteImageView;

    private boolean isFavorite = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final int movieId = intent.getIntExtra(MainActivity.EXTRA_MOVIE_DETAILS, 2);

        mMakeFavoriteImageView = (SparkButton) findViewById(R.id.toggle_favorite);

        // Check if the movies is in the favorites library or not
        new AsyncTask<String, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                Cursor c = getContentResolver().query(MoviesContract.FavoritesEntry.buildMovieUriWithID(String.valueOf(movieId)),null,null,null,null);
                int count = c.getCount();
                if(c.moveToFirst() || count > 0)
                    return true;
                else
                    return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                isFavorite = aBoolean;
                if(aBoolean)
                    mMakeFavoriteImageView.setChecked(true);
                else
                    mMakeFavoriteImageView.setChecked(false);
            }
        }.execute();

        // Binding movie details into ui
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
                    mMDetails = movieDetail;
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

        mMakeFavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFavorite) {
                    ContentValues cv = new ContentValues();
                    cv.put(MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID, movieId);
                    cv.put(MoviesContract.FavoritesEntry.COLUMN_POSTER_PATH, mMDetails.getPosterPath());
                    cv.put(MoviesContract.FavoritesEntry.COLUMN_TITLE, mMDetails.getTitle());
                    getContentResolver().insert(MoviesContract.FavoritesEntry.CONTENT_URI, cv);
                    Toast.makeText(DetailsActivity.this, mMDetails.getTitle()+" Added To Favorites", Toast.LENGTH_SHORT).show();
                    isFavorite = true;
                    mMakeFavoriteImageView.setChecked(true);
                    mMakeFavoriteImageView.playAnimation();


                }else{
                    getContentResolver().delete(MoviesContract.FavoritesEntry.CONTENT_URI, "movie_id = "+ movieId, null );
                    Toast.makeText(DetailsActivity.this, mMDetails.getTitle()+" Removed From Favorites", Toast.LENGTH_SHORT).show();
                    isFavorite = false;
                    mMakeFavoriteImageView.setChecked(false);
                    mMakeFavoriteImageView.playAnimation();

                }
            }
        });

        // Trailers Recyclerview setup
        mTrailersRecyclerview = (RecyclerView) findViewById(R.id.trailer_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mTrailersRecyclerview.setLayoutManager(gridLayoutManager);
        mTrailersRecyclerview.setHasFixedSize(true);

        mTrailersAdapter = new TrailersAdapter(this);
        mTrailersRecyclerview.setAdapter(mTrailersAdapter);

        // Reviews Recyclerview setup
        mReviewsRecyclerview = (RecyclerView) findViewById(R.id.reviews_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mReviewsRecyclerview.setLayoutManager(linearLayoutManager);
        mReviewsRecyclerview.setHasFixedSize(true);

        mReviewsAdapter = new ReviewsAdapter(this);
        mReviewsRecyclerview.setAdapter(mReviewsAdapter);


        new FetchTralersTask(this){
            @Override
            protected void onPostExecute(Trailer[] trailers) {
                mTrailersAdapter.setNewData(trailers);
            }
        }.execute(movieId);

        new FetchReviewsTask(this){
            @Override
            protected void onPostExecute(Review[] reviews) {
                mReviewsAdapter.setNewData(reviews);
            }
        }.execute(movieId);
    }

    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        detailsInfo.setVisibility(View.VISIBLE);
        mMakeFavoriteImageView.playAnimation();
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        detailsInfo.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(Trailer trailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailer.getSource())));
    }
}
