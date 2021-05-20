package com.app.tmdb.interfaces;

public interface MoviesAdapterListener {

    void fetchNextPageResponse(int recyclerViewID);
    void showMovieDetails(long movie_id);
}
