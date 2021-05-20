package com.app.tmdb.models;

import java.io.Serializable;
import java.util.List;

public class MovieDetailsResponse implements Serializable {

    boolean adult, video;
    int runtime, vote_count;
    long id, budget, revenue;
    float vote_average;
    String backdrop_path, poster_path, imdb_id, overview, original_title, title, release_date,
            tagline, status, original_language, homepage;
    List<Genre> genres;
    List<Language> spoken_languages;
    List<ProductionCompany> production_companies;
    List<ProductionCountry> production_countries;
    Collection belongs_to_collection;

    public boolean isAdult() {
        return adult;
    }

    public boolean isVideo() {
        return video;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getVote_count() {
        return vote_count;
    }

    public long getId() {
        return id;
    }

    public long getBudget() {
        return budget;
    }

    public long getRevenue() {
        return revenue;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTagline() {
        return tagline;
    }

    public String getStatus() {
        return status;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getHomepage() {
        return homepage;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Language> getSpoken_languages() {
        return spoken_languages;
    }

    public List<ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public List<ProductionCountry> getProduction_countries() {
        return production_countries;
    }

    public Collection getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public static class Genre implements Serializable {
        long id;
        String name;

        public String getName() {
            return name;
        }
    }

    public static class Language implements Serializable {
        String iso_639_1, name;

        public String getName() {
            return name;
        }
    }

    public static class ProductionCompany implements Serializable {
        long id;
        String logo_path, name, origin_country;
    }

    public static class ProductionCountry implements Serializable {
        String iso_3166_1, name;

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public String getName() {
            return name;
        }
    }

    public static class Collection implements Serializable {
        long id;
        String name, poster_path, backdrop_path;
    }
}
