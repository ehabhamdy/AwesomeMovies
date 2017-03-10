package com.ehab.awesomemovies.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ehab.awesomemovies.MoviesOnClickListener;
import com.ehab.awesomemovies.R;
import com.ehab.awesomemovies.model.MovieDetail;
import com.ehab.awesomemovies.ui.fragments.FavoritesFragment;
import com.ehab.awesomemovies.ui.fragments.MoviesFragment;


public class MainActivity extends AppCompatActivity implements MoviesOnClickListener {


    public static final String EXTRA_MOVIE_DETAILS = "movie-details";
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void onListItemClick(MovieDetail movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_DETAILS, movie.getId());
        startActivity(intent);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return MoviesFragment.newInstance(1);
                case 1:
                    return MoviesFragment.newInstance(2);
                case 2:
                    return FavoritesFragment.newInstance(3);
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Popular Movies";
                case 1:
                    return "Top Rated";
                case 2:
                    return "favorites";
            }
            return null;
        }

    }
}
