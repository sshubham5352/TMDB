package com.app.tmdb.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.app.tmdb.models.MovieCreditsResponse;
import com.app.tmdb.models.MovieDetailsResponse;
import com.app.tmdb.models.MovieResponse;
import com.app.tmdb.models.MovieReviewsResponse;
import com.app.tmdb.models.MultiMediaSearchResponse;
import com.app.tmdb.models.TvShowDetailsResponse;
import com.app.tmdb.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {
    private final Context mContext;
    public static final String key = Constants.API_KEY;
    private final ApiResponseInterface mApiResponseInterface;
    private final ApiInterface apiInterface;
    ProgressDialog progressDialog;
    private boolean isDataLoading;

    public ApiManager(Context context, ApiResponseInterface apiResponseInterface) {
        this.mContext = context;
        this.mApiResponseInterface = apiResponseInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        isDataLoading = false;
    }

    public void getTrendingResponse(final String timeWindow, final int page) {
        if (page == 1)
            progressDialog.show();
        else if (isDataLoading)
            return;
        else
            isDataLoading = true;

        Call<MovieResponse> call = apiInterface.getTrendingResponse(timeWindow, key, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.TRENDING_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mApiResponseInterface.apiCallFailure("Network Error");
            }
        });
        progressDialog.dismiss();
        isDataLoading = false;
    }

    public void getPopularResponse(String category, final int page) {
        if (page == 1)
            progressDialog.show();
        else if (isDataLoading)
            return;
        else
            isDataLoading = true;

        Call<MovieResponse> call = apiInterface.getPopularMovieResponse(category, key, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.POPULAR_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
        progressDialog.dismiss();
        isDataLoading = false;
    }

    public void getPopularOnTVResponse(final int page) {
        if (page == 1)
            progressDialog.show();
        else if (isDataLoading)
            return;
        else
            isDataLoading = true;

        Call<MovieResponse> call = apiInterface.getPopularOnTVResponse(key, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.POPULAR_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
        progressDialog.dismiss();
        isDataLoading = false;
    }

    public void getPopularUpcomingResponse(final int page) {
        if (page == 1)
            progressDialog.show();
        else if (isDataLoading)
            return;
        else
            isDataLoading = true;

        Call<MovieResponse> call = apiInterface.getPopularUpcomingResponse(key, page, "US");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.POPULAR_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
        progressDialog.dismiss();
        isDataLoading = false;
    }

    public void getTopRatedResponse(String contentType, final int page) {
        if (page == 1)
            progressDialog.show();
        else if (isDataLoading)
            return;
        else
            isDataLoading = true;

        Call<MovieResponse> call = apiInterface.getTopRatedResponse(contentType, key, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.TOP_RATED_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
        progressDialog.dismiss();
        isDataLoading = false;
    }

    public void getMultiMediaResponse(final String searchTxt) {
        Call<MultiMediaSearchResponse> call = apiInterface.getMultiSearchResult(key, searchTxt, 1, false);
        call.enqueue(new Callback<MultiMediaSearchResponse>() {
            @Override
            public void onResponse(Call<MultiMediaSearchResponse> call, Response<MultiMediaSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.MULTI_MEDIA_SEARCH_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<MultiMediaSearchResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getMovieDetails(final long movieID) {

        Call<MovieDetailsResponse> call = apiInterface.getMovieDetails(movieID, key);
        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.MOVIE_DETAILS_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getTvShowDetails(final long tvID) {
        Call<TvShowDetailsResponse> call = apiInterface.getTvShowDetails(tvID, key);
        call.enqueue(new Callback<TvShowDetailsResponse>() {

            @Override
            public void onResponse(Call<TvShowDetailsResponse> call, Response<TvShowDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.TV_SHOW_DETAILS_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
            }

            @Override
            public void onFailure(Call<TvShowDetailsResponse> call, Throwable t) {
                Toast.makeText(mContext, "we have stuck here", Toast.LENGTH_SHORT).show();
                mApiResponseInterface.apiCallFailure("Network Error");
            }
        });
    }

    public void getMovieCredits(final long movieID) {
        progressDialog.show();
        Call<MovieCreditsResponse> call = apiInterface.getMovieCredits(movieID, key);
        call.enqueue(new Callback<MovieCreditsResponse>() {
            @Override
            public void onResponse(Call<MovieCreditsResponse> call, Response<MovieCreditsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.MOVIE_CREDITS_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieCreditsResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    public void getMovieReviews(final long movieID, int page) {
        progressDialog.show();
        Call<MovieReviewsResponse> call = apiInterface.getMovieReviews(movieID, key, page);
        call.enqueue(new Callback<MovieReviewsResponse>() {
            @Override
            public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.apiCallSuccess(response.body(), Constants.MOVIE_REVIEWS_RESPONSE);
                } else if (response.code() == 401) {
                    mApiResponseInterface.apiCallFailure("Unauthorized Call");
                } else if (response.code() == 404) {
                    mApiResponseInterface.apiCallFailure("Invalid Request");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}


