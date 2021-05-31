package com.app.tmdb.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.tmdb.R;
import com.app.tmdb.adapters.CastResponseAdapter;
import com.app.tmdb.adapters.CrewResponseAdapter;
import com.app.tmdb.adapters.MovieDetailsPagerAdapter;
import com.app.tmdb.databinding.FragmentMovieStarCastBinding;
import com.app.tmdb.interfaces.StarCastAdapterListener;
import com.app.tmdb.models.MovieCreditsResponse;
import com.app.tmdb.retrofit.ApiManager;
import com.app.tmdb.retrofit.ApiResponseInterface;
import com.app.tmdb.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MovieStarCastFragment extends Fragment implements ApiResponseInterface, StarCastAdapterListener {
    //field declaration
    FragmentMovieStarCastBinding binding;
    MovieCreditsResponse movieCreditsResponse;
    MovieDetailsPagerAdapter pagerAdapter;
    ApiManager apiManager;

    // constructor
    public MovieStarCastFragment(MovieDetailsPagerAdapter adapter) {
        super();
        this.pagerAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_star_cast, container, false);
        apiManager = new ApiManager(getContext(), this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setting layoutManagers in RecyclerViews
        binding.topBilledCastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.crewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        //attaching SnapHelper
        new LinearSnapHelper().attachToRecyclerView(binding.topBilledCastRecyclerView);
        new LinearSnapHelper().attachToRecyclerView(binding.crewRecyclerView);
        // fetching data
        apiManager.getMovieCredits(getArguments().getLong(Constants.FRAGMENT_STAR_CAST_BUNDLE));
    }


    private List<MovieCreditsResponse.Crew> getArrangeCrewMembers() {
        List<MovieCreditsResponse.Crew> crew = new ArrayList<>();
        // adding directors
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Director"))
                crew.add(crewMember);
        }
        // adding story writers
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Story"))
                crew.add(crewMember);
        }
        // adding novel writer
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Novel"))
                crew.add(crewMember);
        }
        // adding comic book writer
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Comic Book"))
                crew.add(crewMember);
        }
        // adding producers
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Producer"))
                crew.add(crewMember);
        }
        // adding music screenplay writers
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Screenplay"))
                crew.add(crewMember);
        }
        // adding music director
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Music"))
                crew.add(crewMember);
        }
        // adding music casting director
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Casting"))
                crew.add(crewMember);
        }
        // adding playback singers
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("Playback Singer"))
                crew.add(crewMember);
        }
        // adding in memory of
        for (MovieCreditsResponse.Crew crewMember : movieCreditsResponse.getCrew()) {
            if (crewMember.getJob().matches("In Memory Of"))
                crew.add(crewMember);
        }

        return crew;
    }

    @Override
    public void apiCallSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constants.MOVIE_CREDITS_RESPONSE) {
            movieCreditsResponse = (MovieCreditsResponse) response;
            binding.topBilledCastRecyclerView.setAdapter(new CastResponseAdapter(getContext(),
                    this, movieCreditsResponse.getCast()));
            binding.crewRecyclerView.setAdapter(new CrewResponseAdapter(getContext(), this, getArrangeCrewMembers()));
            setStarCastLayoutHeight();
        }
    }

    @Override
    public void apiCallFailure(String errorCode) {
        Toast.makeText(getContext(), errorCode, Toast.LENGTH_LONG).show();
    }

    //passing height of the overview layout to the adapter
    private void setStarCastLayoutHeight() {
        binding.rootLayout.post(new Runnable() {
            @Override
            public void run() {
                pagerAdapter.setStarCastLayoutHeight(binding.rootLayout.getHeight());
            }
        });
    }


    @Override
    public void lastItemReached(int recyclerViewID) {
        if (recyclerViewID == R.id.top_billed_cast_RecyclerView) {
            binding.castNoMoreItemsLayout.setVisibility(View.VISIBLE);
            LottieAnimationView anim = binding.castNoMoreItemsLayout.findViewById(R.id.cast_no_more_items_anim);
            anim.playAnimation();
            anim.setRepeatCount(ValueAnimator.INFINITE);
        }
        if (recyclerViewID == R.id.crew_RecyclerView) {
            binding.crewNoMoreItemsLayout.setVisibility(View.VISIBLE);
            LottieAnimationView anim = binding.crewNoMoreItemsLayout.findViewById(R.id.crew_no_more_items_anim);
            anim.playAnimation();
            anim.setRepeatCount(ValueAnimator.INFINITE);
        }
    }

    @Override
    public void hideNoMoreItemsLayout(int recyclerViewID) {
        if (recyclerViewID == R.id.top_billed_cast_RecyclerView)
            binding.castNoMoreItemsLayout.setVisibility(View.GONE);
        if (recyclerViewID == R.id.crew_RecyclerView)
            binding.crewNoMoreItemsLayout.setVisibility(View.GONE);
    }


}
