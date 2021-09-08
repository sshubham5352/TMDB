package com.app.tmdb.utils;

public class Constants {
    //FINAL STATIC FIELDS
    public static final String API_KEY = "9cc33fba6d2372e8adad32f3f9c3575b";

    //TMDB URLS
    public static final String IMG_ORIGINAL_BASE_URL = "https://image.tmdb.org/t/p/original/";
    public static final String IMG_W500_BASE_URL = "https://image.tmdb.org/t/p/w500/";
    public static final String IMG_W220_H330_BASE_URL = "https://image.tmdb.org/t/p/w220_and_h330_face/";
    public static final String IMG_W300_H450_BASE_URL = "http://image.tmdb.org/t/p/w300_and_h450_bestv2/";
    public static final String IMG_WIDE_H450_BASE_URL = "https://image.tmdb.org/t/p/w1000_and_h450_multi_faces/";
    public static final String IMG_W1066_H600_BASE_URL = "https://image.tmdb.org/t/p/w1066_and_h600_bestv2/";
    public static final String IMG_BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1920_and_h800_multi_faces";
    public static final String IMG_BLUETONE_BASE_URL = "https://image.tmdb.org/t/p/w1066_and_h600_multi_faces_filter(duotone,032541,01b4e4)/";
    public static final String IMG_BLUETONE_BASE_URL_Portrait = "https://image.tmdb.org/t/p/w600_and_h900_multi_faces_filter(duotone,032541,01b4e4)/";
    public static final String IMG_H60_BASE_URL = "https://www.themoviedb.org/t/p/h60/";

    //RETROFIT CONSTANTS
    public static final int TRENDING_RESPONSE = 1;
    public static final int POPULAR_RESPONSE = 2;
    public static final int TOP_RATED_RESPONSE = 3;
    public static final int MULTI_MEDIA_SEARCH_RESPONSE = 4;
    public static final int MOVIE_DETAILS_RESPONSE = 5;
    public static final int TV_SHOW_DETAILS_RESPONSE = 6;
    public static final int MOVIE_CREDITS_RESPONSE = 7;
    public static final int MOVIE_REVIEWS_RESPONSE = 8;

    //SESSION MANAGER CONSTANTS
    public static final int LOGGED_IN_VIA_GOOGLE = 1;
    public static final int LOGGED_IN_VIA_FACEBOOK = 2;
    public static final int LOGGED_IN_VIA_MANUAL_LOGIN = 3;

    //RECYCLERVIEW CONSTANTS
    public static final String TODAY = "day";
    public static final String THIS_WEEK = "week";
    public static final String STREAMING = "popular";
    public static final String IN_THEATERS = "now_playing";
    public static final String MOVIES = "movie";
    public static final String TV = "tv";

    //MODEL CONSTANTS
    public static final String SEARCHED_MOVIE_ID = "movie_id";
    public static final String SEARCHED_TV_ID = "tv_id";


    //FRAGMENT CONSTANTS
    public static final String FRAGMENT_OVERVIEW_BUNDLE = "1";
    public static final String FRAGMENT_STAR_CAST_BUNDLE = "2";
    public static final String FRAGMENT_REVIEWS_BUNDLE = "3";

    //LOG CONSTANTS
    public static final String LOG_MOVIES = "movies";
    public static final String LOG_API_CALL = "API_CALL";
}
