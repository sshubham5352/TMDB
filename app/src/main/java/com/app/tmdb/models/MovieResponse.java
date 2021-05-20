package com.app.tmdb.models;

import java.util.List;

public class MovieResponse {

    int page, total_results, total_pages;
    List<Result> results;

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public List<Result> getResults() {
        return results;
    }

    public static class Result {
        long id;
        boolean video, adult;
        double popularity;
        int vote_count;
        float vote_average;
        List<Integer> genre_ids;
        String title, name, release_date, first_air_date, original_language, original_title, backdrop_path,
                overview, poster_path, media_type;

        public long getId() {
            return id;
        }

        public boolean isVideo() {
            return video;
        }

        public boolean isAdult() {
            return adult;
        }

        public double getPopularity() {
            return popularity;
        }

        public int getVote_count() {
            return vote_count;
        }

        public float getVote_average() {
            return vote_average;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public String getTitle() {
            return title;
        }

        public String getName() {
            return name;
        }

        public String getRelease_date() {
            return release_date;
        }

        public String getFirst_air_date() {
            return first_air_date;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public String getOverview() {
            return overview;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public String getMedia_type() {
            return media_type;
        }
    }
}

