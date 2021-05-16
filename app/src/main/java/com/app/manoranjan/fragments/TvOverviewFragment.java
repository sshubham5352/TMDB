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
import com.app.manoranjan.adapters.TvShowDetailsPagerAdapter;
import com.app.manoranjan.databinding.FragmentTvOverviewBinding;
import com.app.manoranjan.models.TvShowDetailsResponse;
import com.app.manoranjan.utils.Constants;
import com.app.manoranjan.utils.Helper;

public class TvOverviewFragment extends Fragment {
    //class variables
    TvShowDetailsResponse mResponse;
    FragmentTvOverviewBinding binding;
    TvShowDetailsPagerAdapter adapter;
    int rootLayoutVisibility;


    public TvOverviewFragment(TvShowDetailsPagerAdapter adapter) {
        super();        // try to remove it and see what happens
        this.adapter = adapter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_overview, container, false);
        binding.rootLayout.setVisibility(View.INVISIBLE);
        mResponse = (TvShowDetailsResponse) getArguments().getSerializable(Constants.FRAGMENT_OVERVIEW_BUNDLE);
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
        Helper.setText(mResponse.getName(), binding.tvShowTitle, true);
        //setting network
        try {
            Helper.setW500Image(mResponse.getNetworks().get(0).getLogo_path(), binding.network);
        } catch (Exception e) {
            binding.network.setVisibility(View.GONE);
            binding.txtNetwork.setVisibility(View.GONE);
        }

        //setting original title
        if (mResponse.getOriginal_name().equals(mResponse.getName())) {
            binding.txtOriginalTitle.setVisibility(View.GONE);
            binding.originalTitle.setVisibility(View.GONE);
        } else
            Helper.setText(mResponse.getOriginal_name(), binding.originalTitle, true);
        //setting overview
        Helper.setText(mResponse.getOverview(), binding.overview, true);
        //setting status
        Helper.setText(mResponse.getStatus(), binding.status, true);
        //setting release date
        Helper.setDate(mResponse.getFirst_air_date(), null, binding.firstAirDate);
        //setting running time
        Helper.setTimeDuration(mResponse.getEpisode_run_time()[0], binding.episodeDuration);
        //setting original language
        Helper.setOriginalLanguage(mResponse.getOriginal_language(), binding.originalLanguage, true);
        //passing height of the overview layout to the adapter
        binding.rootLayout.post(new Runnable() {
            @Override
            public void run() {
                adapter.setOverViewLayoutHeight(binding.rootLayout.getHeight());
            }
        });
    }
}