package com.app.manoranjan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.manoranjan.R;
import com.app.manoranjan.adapters.MovieDetailsPagerAdapter;
import com.app.manoranjan.databinding.FragmentMovieOverviewBinding;
import com.app.manoranjan.models.MovieDetailsResponse;
import com.app.manoranjan.utils.Constants;
import com.app.manoranjan.utils.Helper;

public class MovieOverviewFragment extends Fragment {
    //class fields
    MovieDetailsResponse mResponse;
    FragmentMovieOverviewBinding binding;
    MovieDetailsPagerAdapter adapter;
    int rootLayoutVisibility;

    public MovieOverviewFragment(MovieDetailsPagerAdapter adapter) {
        super();        // try to remove it and see what happens
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_overview, container, false);
        binding.rootLayout.setVisibility(View.INVISIBLE);
        mResponse = (MovieDetailsResponse) getArguments().getSerializable(Constants.FRAGMENT_OVERVIEW_BUNDLE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
        if (rootLayoutVisibility == -1) {
            setOverviewVisible();
            rootLayoutVisibility = 1;
        }
    }


    public void setOverviewVisible() {
        if (binding == null) {
            rootLayoutVisibility = -1;
            return;
        }
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        binding.rootLayout.startAnimation(anim);
        binding.rootLayout.setVisibility(View.VISIBLE);
        rootLayoutVisibility = 1;
    }

    private void setData() {
        //setting title
        Helper.setText(mResponse.getTitle(), binding.movieTitle, true);
        //setting original title
        if (mResponse.getOriginal_title().equals(mResponse.getTitle())) {
            binding.txtOriginalTitle.setVisibility(View.GONE);
            binding.originalTitle.setVisibility(View.GONE);
        } else
            Helper.setText(mResponse.getOriginal_title(), binding.originalTitle, true);
        //setting overview
        Helper.setText(mResponse.getOverview(), binding.overview, true);
        //setting status
        Helper.setText(mResponse.getStatus(), binding.status, true);
        //setting release date
        Helper.setDate(mResponse.getRelease_date(), null, binding.releaseDate);
        //setting running time
        Helper.setTimeDuration(mResponse.getRuntime(), binding.runningTime);
        //setting original language
        Helper.setOriginalLanguage(mResponse.getOriginal_language(), binding.originalLanguage, true);
        //setting budget
        try {
            Helper.setAmount(getContext(), mResponse.getBudget(),
                    mResponse.getProduction_countries().get(0).getIso_3166_1(),
                    binding.budget, true);
        } catch (IndexOutOfBoundsException e) {

        }

        //setting revenue
        try {
            Helper.setAmount(getContext(),
                    mResponse.getRevenue(),
                    mResponse.getProduction_countries().get(0).getIso_3166_1(),
                    binding.revenue, true);
        } catch (IndexOutOfBoundsException e) {

        }


        //passing height of the overview layout to the adapter
        binding.rootLayout.post(new Runnable() {
            @Override
            public void run() {
                adapter.setOverViewLayoutHeight(binding.rootLayout.getHeight());
            }
        });
    }
}