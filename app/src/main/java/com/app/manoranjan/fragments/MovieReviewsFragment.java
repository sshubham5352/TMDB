package com.app.manoranjan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.manoranjan.R;
import com.app.manoranjan.adapters.MovieDetailsPagerAdapter;
import com.app.manoranjan.databinding.FragmentMovieReviewsBinding;

public class MovieReviewsFragment extends Fragment {
    //field declaration
    FragmentMovieReviewsBinding binding;
    MovieDetailsPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_reviews, container, false);
        return binding.getRoot();
    }


    public MovieReviewsFragment(MovieDetailsPagerAdapter adapter) {
        super();
        this.adapter = adapter;
    }
}
