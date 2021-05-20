package com.app.tmdb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.tmdb.adapters.TvShowDetailsPagerAdapter;

public class TvStarCastFragment extends Fragment {
    //class variables
    TvShowDetailsPagerAdapter adapter;


    public TvStarCastFragment(TvShowDetailsPagerAdapter adapter) {
        super();        // try to remove it and see what happens
        this.adapter = adapter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
