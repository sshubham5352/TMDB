package com.app.tmdb.retrofit;

import com.app.tmdb.models.MovieCreditsResponse;
import com.app.tmdb.models.MovieDetailsResponse;
import com.app.tmdb.models.MovieResponse;
import com.app.tmdb.models.MovieReviewsResponse;
import com.app.tmdb.models.MultiMediaSearchResponse;
import com.app.tmdb.models.TvShowDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("trending/movie/{time_window}")
    Call<MovieResponse> getTrendingResponse(@Path("time_window") String timeWindow,
                                            @Query("api_key") String key,
                                            @Query("page") int page);

    @GET("movie/{category}")
    Call<MovieResponse> getPopularMovieResponse(@Path("category") String category,
                                                @Query("api_key") String key,
                                                @Query("page") int page);

    @GET("tv/on_the_air")
    Call<MovieResponse> getPopularOnTVResponse(@Query("api_key") String key,
                                               @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieResponse> getPopularUpcomingResponse(@Query("api_key") String key,
                                                   @Query("page") int page,
                                                   @Query("region") String region);

    @GET("{content_type}/top_rated")
    Call<MovieResponse> getTopRatedResponse(@Path("content_type") String contentType,
                                            @Query("api_key") String key,
                                            @Query("page") int page);

    @GET("search/multi")
    Call<MultiMediaSearchResponse> getMultiSearchResult(@Query("api_key") String key,
                                                        @Query("query") String searchedTxt,
                                                        @Query("page") int page,
                                                        @Query("include_adult") boolean includeAdult);

    @GET("movie/{movie_id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("movie_id") long movieID,
                                               @Query("api_key") String key);

    @GET("tv/{tv_id}")
    Call<TvShowDetailsResponse> getTvShowDetails(@Path("tv_id") long tvID,
                                                 @Query("api_key") String key);

    @GET("movie/{movie_id}/credits")
    Call<MovieCreditsResponse> getMovieCredits(@Path("movie_id") long movieID,
                                               @Query("api_key") String key);

    @GET("movie/{movie_id}/reviews")
    Call<MovieReviewsResponse> getMovieReviews(@Path("movie_id") long movieID,
                                               @Query("api_key") String key, @Query("page") int page);
}
