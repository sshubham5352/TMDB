package com.app.tmdb.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.tmdb.fragments.MovieOverviewFragment;
import com.app.tmdb.fragments.MovieReviewsFragment;
import com.app.tmdb.fragments.MovieStarCastFragment;

public class MovieDetailsPagerAdapter extends FragmentStateAdapter {
    //Field Declaration
    MovieOverviewFragment movieOverviewFragment;
    MovieStarCastFragment movieStarCastFragment;
    MovieReviewsFragment movieReviewsFragment;
    int overViewLayoutHeight, starCastLayoutHeight, reviewLayoutHeight, totalFragments;

    //constructor
    public MovieDetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                                    Bundle fragmentOverviewBundle,
                                    Bundle fragmentStarCastBundle,
                                    Bundle fragmentReviewsBundle,
                                    int totalFragments) {
        super(fragmentActivity);
        this.totalFragments = totalFragments;
        movieOverviewFragment = new MovieOverviewFragment(this);
        movieStarCastFragment = new MovieStarCastFragment(this);
        movieReviewsFragment = new MovieReviewsFragment(this);
        //setting bundles
        movieOverviewFragment.setArguments(fragmentOverviewBundle);
        movieStarCastFragment.setArguments(fragmentStarCastBundle);
        movieReviewsFragment.setArguments(fragmentReviewsBundle);
    }


    @Override
    public int getItemCount() {
        return totalFragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return movieStarCastFragment;
            case 2:
                return movieReviewsFragment;
            case 0:
            default:
                return movieOverviewFragment;

        }
    }


    public void setOverViewLayoutHeight(int overViewLayoutHeight) {
        this.overViewLayoutHeight = overViewLayoutHeight;
    }

    public void setStarCastLayoutHeight(int starCastLayoutHeight) {
        this.starCastLayoutHeight = starCastLayoutHeight;
    }

    public void setReviewLayoutHeight(int reviewLayoutHeight) {
        this.reviewLayoutHeight = reviewLayoutHeight;
    }

    public MovieOverviewFragment getMovieOverviewFragment() {
        return movieOverviewFragment;
    }

    public int getCorrectViewPagerHeight(int selectedTab) {
        switch (selectedTab) {
            case 0:
                return overViewLayoutHeight;
            case 1:
                return starCastLayoutHeight;
            case 2:
                return reviewLayoutHeight;
            default:
                return 0;
        }
    }

}
