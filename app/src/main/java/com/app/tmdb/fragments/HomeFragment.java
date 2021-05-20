package com.app.tmdb.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.activities.MovieDetailsActivity;
import com.app.tmdb.activities.TvShowDetailsActivity;
import com.app.tmdb.adapters.MoviesAdapter;
import com.app.tmdb.adapters.MultiMediaSearchAdapter;
import com.app.tmdb.databinding.FragmentHomeBinding;
import com.app.tmdb.interfaces.MoviesAdapterListener;
import com.app.tmdb.models.MovieResponse;
import com.app.tmdb.models.MultiMediaSearchResponse;
import com.app.tmdb.retrofit.ApiManager;
import com.app.tmdb.retrofit.ApiResponseInterface;
import com.app.tmdb.utils.Constants;
import com.app.tmdb.utils.Helper;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment implements ApiResponseInterface, MoviesAdapterListener {
    //global variables
    Context mContext;
    FragmentHomeBinding binding;
    ApiManager apiManager;
    MovieResponse trendingMovieResponse;
    MovieResponse popularMovieResponse;
    MovieResponse topRatedMovieResponse;
    MultiMediaSearchResponse multiSearchResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
            mContext = getContext();
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (trendingMovieResponse != null)
            return;


        setTags();
        fixLayout();
        trendingMovieResponse = new MovieResponse();
        popularMovieResponse = new MovieResponse();
        topRatedMovieResponse = new MovieResponse();
        multiSearchResponse = new MultiMediaSearchResponse();
        binding.whatsPopularRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        binding.topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        binding.trendingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        apiManager = new ApiManager(mContext, this);
        apiManager.getTrendingResponse(Constants.TODAY, 1);
        apiManager.getPopularResponse(Constants.STREAMING, 1);
        apiManager.getTopRatedResponse(Constants.MOVIES, 1);
        setSearchBarTextWatcher();
        setMultiSearchItemOnClickListener();
        //Adding OnTabSelectedListener
        binding.trendingTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    binding.trendingRecyclerView.setTag(Constants.TODAY);
                else
                    binding.trendingRecyclerView.setTag(Constants.THIS_WEEK);

                trendingMovieResponse.setPage(0);
                binding.trendingRecyclerView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_out));
                binding.trendingRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.trendingRecyclerView.setVisibility(View.INVISIBLE);
                        fetchData(R.id.trending_RecyclerView);
                    }
                }, 500);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.whatsPopularTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0)
                    binding.whatsPopularRecyclerView.setTag(Constants.STREAMING);
                else if (tab.getPosition() == 2)
                    binding.whatsPopularRecyclerView.setTag(Constants.IN_THEATERS);

                popularMovieResponse.setPage(0);
                binding.whatsPopularRecyclerView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_out));
                binding.whatsPopularRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.whatsPopularRecyclerView.setVisibility(View.INVISIBLE);
                        fetchData(R.id.whats_popular_RecyclerView);
                    }
                }, 500);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.topRatedTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    binding.topRatedRecyclerView.setTag(Constants.MOVIES);
                else if (tab.getPosition() == 1)
                    binding.topRatedRecyclerView.setTag(Constants.TV);

                topRatedMovieResponse.setPage(0);
                binding.topRatedRecyclerView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_out));
                binding.topRatedRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.topRatedRecyclerView.setVisibility(View.INVISIBLE);
                        fetchData(R.id.top_rated_RecyclerView);
                    }
                }, 500);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            binding.searchBar.dismissDropDown();
        } catch (Exception e) {
            //
        }
    }


    private void setTags() {
        binding.trendingRecyclerView.setTag(Constants.TODAY);
        binding.whatsPopularRecyclerView.setTag(Constants.STREAMING);
        binding.topRatedRecyclerView.setTag(Constants.MOVIES);
    }

    private void fixLayout() {
        float density = Helper.getDensity();
        binding.searchBar.setDropDownVerticalOffset(5);
        binding.searchBar.setPadding((int) (16 * density), 0, getPaddingEnd(), 0);
//        //search container background image height according to screen size
//        if (Helper.isScreenSmallerThan6(getActivity())) {
//            binding.searchContainer.getLayoutParams().height = (int) (154 * density);
//            if (binding.welcomeTxt.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) binding.welcomeTxt.getLayoutParams();
//                p.setMargins(0, (int) (30 * density), 0, 0);
//            }
//        }
    }

    private int getPaddingEnd() {
        binding.searchBtn.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        return binding.searchBtn.getMeasuredWidth();
    }

    private void setSearchContainerBackground() {
        String backdrop_path = "";
        int random;
        while (backdrop_path == null || backdrop_path.length() == 0) {
            random = Helper.randomInt(trendingMovieResponse.getResults().size());
            backdrop_path = trendingMovieResponse.getResults().get(random).getBackdrop_path();
        }
        Picasso.get().load(Constants.IMG_BLUETONE_BASE_URL + backdrop_path).into(binding.dynamicBgImg);
    }

    private void setSearchBarTextWatcher() {
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 2) {
                    (binding.searchBar).dismissDropDown();
                    apiManager.getMultiMediaResponse(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setMultiSearchItemOnClickListener() {
        binding.searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                binding.searchBar.getText().clear();
                switch (multiSearchResponse.getResults().get(i).getMedia_type()) {
                    case ("movie"):
                        startMovieDetailsActivity(multiSearchResponse.getResults().get(i).getId());
                        break;
                    case ("tv"):
                        startTvShowDetailsActivity(multiSearchResponse.getResults().get(i).getId());
                        break;
                }
            }
        });
    }


    @Override
    public void apiCallSuccess(Object response, int ServiceCode) {
        int page = -1;

        if (ServiceCode != Constants.MULTI_MEDIA_SEARCH_RESPONSE)
            page = ((MovieResponse) response).getPage();

        if (ServiceCode == Constants.TRENDING_RESPONSE) {
            if (page == 1) {
                trendingMovieResponse = (MovieResponse) response;
                binding.trendingRecyclerView.setAdapter(new MoviesAdapter(this, mContext,
                        binding.trendingRecyclerView.getId(),
                        trendingMovieResponse));
                binding.trendingProgressBar.setVisibility(View.GONE);
                binding.trendingRecyclerView.setVisibility(View.VISIBLE);
                if (binding.dynamicBgImg.getDrawable() == null)
                    setSearchContainerBackground();
                return;
            }
            trendingMovieResponse.setPage(page);
            trendingMovieResponse.getResults().addAll(((MovieResponse) response).getResults());
            binding.trendingRecyclerView.getAdapter().notifyDataSetChanged();
            binding.trendingRecyclerView.setVisibility(View.VISIBLE);
            binding.trendingProgressBar.setVisibility(View.GONE);
        }
        if (ServiceCode == Constants.POPULAR_RESPONSE) {
            if (page == 1) {
                popularMovieResponse = (MovieResponse) response;
                binding.whatsPopularRecyclerView.setAdapter(new MoviesAdapter(this, mContext,
                        binding.whatsPopularRecyclerView.getId(),
                        popularMovieResponse));
                binding.popularProgressBar.setVisibility(View.GONE);
                binding.whatsPopularRecyclerView.setVisibility(View.VISIBLE);
                return;
            }
            popularMovieResponse.setPage(page);
            popularMovieResponse.getResults().addAll(((MovieResponse) response).getResults());
            binding.whatsPopularRecyclerView.getAdapter().notifyDataSetChanged();
            binding.whatsPopularRecyclerView.setVisibility(View.VISIBLE);
            binding.popularProgressBar.setVisibility(View.GONE);
        }
        if (ServiceCode == Constants.TOP_RATED_RESPONSE) {
            if (page == 1) {
                topRatedMovieResponse = (MovieResponse) response;
                binding.topRatedRecyclerView.setAdapter(new MoviesAdapter(this, mContext,
                        binding.topRatedRecyclerView.getId(),
                        topRatedMovieResponse));

                binding.topRatedProgressBar.setVisibility(View.GONE);
                binding.topRatedRecyclerView.setVisibility(View.VISIBLE);
                return;
            }
            topRatedMovieResponse.setPage(page);
            topRatedMovieResponse.getResults().addAll(((MovieResponse) response).getResults());
            binding.topRatedRecyclerView.getAdapter().notifyDataSetChanged();
            binding.topRatedRecyclerView.setVisibility(View.VISIBLE);
            binding.topRatedProgressBar.setVisibility(View.GONE);
        }
        if (ServiceCode == Constants.MULTI_MEDIA_SEARCH_RESPONSE) {
            multiSearchResponse = (MultiMediaSearchResponse) response;
            binding.searchBar.setAdapter(new MultiMediaSearchAdapter(mContext, getActivity(), multiSearchResponse.getResults()));
            binding.searchBar.showDropDown();
        }
    }

    @Override
    public void apiCallFailure(String errorCode) {
        Toast.makeText(mContext, errorCode, Toast.LENGTH_SHORT).show();
        binding.trendingRecyclerView.setVisibility(View.VISIBLE);
        binding.topRatedRecyclerView.setVisibility(View.VISIBLE);
        binding.whatsPopularRecyclerView.setVisibility(View.VISIBLE);
    }

    private void fetchData(int recyclerViewID) {

        if (recyclerViewID == binding.whatsPopularRecyclerView.getId()) {
            binding.popularProgressBar.setVisibility(View.VISIBLE);
            if (binding.whatsPopularTabLayout.getSelectedTabPosition() == 1)
                apiManager.getPopularOnTVResponse(popularMovieResponse.getPage() + 1);
            else if (binding.whatsPopularTabLayout.getSelectedTabPosition() == 3)
                apiManager.getPopularUpcomingResponse(popularMovieResponse.getPage() + 1);
            else
                apiManager.getPopularResponse((String) binding.whatsPopularRecyclerView.getTag(), popularMovieResponse.getPage() + 1);

        } else if (recyclerViewID == binding.topRatedRecyclerView.getId()) {
            binding.topRatedProgressBar.setVisibility(View.VISIBLE);
            apiManager.getTopRatedResponse((String) binding.topRatedRecyclerView.getTag(), topRatedMovieResponse.getPage() + 1);

        } else if (recyclerViewID == binding.trendingRecyclerView.getId()) {
            binding.trendingProgressBar.setVisibility(View.VISIBLE);
            apiManager.getTrendingResponse((String) binding.trendingRecyclerView.getTag(), trendingMovieResponse.getPage() + 1);
        }
    }


    @Override
    public void fetchNextPageResponse(int recyclerViewID) {
        fetchData(recyclerViewID);
    }

    @Override
    public void showMovieDetails(long movie_id) {
        startMovieDetailsActivity(movie_id);
    }


    private void startMovieDetailsActivity(long movie_id) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(Constants.SEARCHED_MOVIE_ID, movie_id);
        startActivity(intent);
    }

    private void startTvShowDetailsActivity(long tv_id) {
        Intent intent = new Intent(getActivity(), TvShowDetailsActivity.class);
        intent.putExtra(Constants.SEARCHED_TV_ID, tv_id);
        startActivity(intent);
    }

}
