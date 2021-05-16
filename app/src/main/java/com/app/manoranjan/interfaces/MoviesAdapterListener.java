package com.app.manoranjan.interfaces;

public interface MoviesAdapterListener {

    void fetchNextPageResponse(int recyclerViewID);
    void showMovieDetails(long movie_id);
}
