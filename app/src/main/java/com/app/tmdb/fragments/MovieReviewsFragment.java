package com.app.tmdb.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.adapters.MovieDetailsPagerAdapter;
import com.app.tmdb.adapters.MovieReviewsAdapter;
import com.app.tmdb.databinding.FragmentMovieReviewsBinding;
import com.app.tmdb.models.MovieReviewsResponse;
import com.app.tmdb.retrofit.ApiManager;
import com.app.tmdb.retrofit.ApiResponseInterface;
import com.app.tmdb.utils.Constants;

public class MovieReviewsFragment extends Fragment implements ApiResponseInterface {
    //field declaration
    Context mContext;
    ApiManager apiManager;
    MovieReviewsResponse movieReviewsResponse;
    MovieDetailsPagerAdapter adapter;
    FragmentMovieReviewsBinding binding;
    int currentPage;

    // constructor
    public MovieReviewsFragment(MovieDetailsPagerAdapter adapter) {
        super();
        this.adapter = adapter;
        currentPage = 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_reviews, container, false);
        apiManager = new ApiManager(getContext(), this);
        mContext = getContext();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setting layoutManagers in RecyclerViews
        binding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));

        // fetching data
        apiManager.getMovieReviews(getArguments().getLong(Constants.FRAGMENT_REVIEWS_BUNDLE), currentPage);
    }


    @Override
    public void apiCallSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constants.MOVIE_REVIEWS_RESPONSE) {
            if (currentPage == 1) {
                movieReviewsResponse = (MovieReviewsResponse) response;
                binding.reviewsRecyclerView.setAdapter(new MovieReviewsAdapter(mContext, movieReviewsResponse));
            } else {
                movieReviewsResponse.getResults().addAll(((MovieReviewsResponse) response).getResults());
                binding.reviewsRecyclerView.getAdapter().notifyDataSetChanged();
            }
            setReviewLayoutHeight();
        }
    }

    @Override
    public void apiCallFailure(String errorCode) {
        Log.d("REVIEWS FRAGMENT", "apiCallFailure: " + errorCode);
        Toast.makeText(getContext(), errorCode, Toast.LENGTH_LONG).show();
    }

    //passing height of the overview layout to the adapter
    private void setReviewLayoutHeight() {
        binding.rootLayout.post(new Runnable() {
            @Override
            public void run() {
                adapter.setReviewLayoutHeight(binding.rootLayout.getHeight());
            }
        });
    }
}
