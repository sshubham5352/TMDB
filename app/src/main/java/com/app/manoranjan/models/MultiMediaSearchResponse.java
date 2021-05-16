package com.app.manoranjan.models;

import androidx.annotation.NonNull;

import com.app.manoranjan.utils.Helper;

import java.util.List;

public class MultiMediaSearchResponse {

    int page, total_results, total_pages;
    List<MultiMediaSearchResponse.Result> results;

    public int getPage() {
        return page;
    }

    public List<MultiMediaSearchResponse.Result> getResults() {
        return results;
    }

    public static class Result {
        long id;
        String title, name, original_title, media_type;


        @NonNull
        @Override
        public String toString() {
            return Helper.getFirstNonNullString(title, name);
        }


        public long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getName() {
            return name;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getMedia_type() {
            return media_type;
        }
    }
}

/*
 * not required fields that I've removed :-
 * int vote_count;
 * double popularity;
 * float vote_average;
 * List<Integer> genre_ids;
 * List<KnownFor> known_for;
 * String  release_date, first_air_date, original_language,overview
 * , profile_path, poster_path, backdrop_path, known_for_department*/
