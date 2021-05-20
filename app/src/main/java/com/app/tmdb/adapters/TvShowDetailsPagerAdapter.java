package com.app.tmdb.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.tmdb.fragments.TvOverviewFragment;
import com.app.tmdb.fragments.TvReviewsFragment;
import com.app.tmdb.fragments.TvStarCastFragment;

public class TvShowDetailsPagerAdapter extends FragmentStateAdapter {
    //Field Declaration
    TvOverviewFragment tvOverviewFragment;
    TvStarCastFragment tvStarCastFragment;
    TvReviewsFragment tvReviewsFragment;
    int overViewLayoutHeight, starCastLayoutHeight, reviewLayoutHeight, totalFragments;

    //constructor
    public TvShowDetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                                     Bundle fragmentOverviewBundle,
                                     Bundle fragmentStarCastBundle,
                                     int totalFragments) {
        super(fragmentActivity);
        this.totalFragments = totalFragments;
        tvOverviewFragment = new TvOverviewFragment(this);
        tvStarCastFragment = new TvStarCastFragment(this);
        tvReviewsFragment = new TvReviewsFragment(this);
        //setting bundles
        tvOverviewFragment.setArguments(fragmentOverviewBundle);
        tvStarCastFragment.setArguments(fragmentStarCastBundle);
    }

    @Override
    public int getItemCount() {
        return totalFragments;
    }


    @Override
    @NonNull
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return tvStarCastFragment;
            case 2:
                return tvReviewsFragment;
            case 0:
            default:
                return tvOverviewFragment;

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

    public TvOverviewFragment getTvOverviewFragment() {
        return tvOverviewFragment;
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
