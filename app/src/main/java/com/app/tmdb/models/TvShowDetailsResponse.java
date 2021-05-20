package com.app.tmdb.models;

import java.io.Serializable;
import java.util.List;

public class TvShowDetailsResponse implements Serializable {

    boolean adult, video;
    int vote_count, number_of_episodes, number_of_seasons;
    int[] episode_run_time;                                                                         // may cause error
    long id;
    float vote_average;
    String backdrop_path;
    String poster_path;
    String overview;

    public boolean isAdult() {
        return adult;
    }

    public boolean isVideo() {
        return video;
    }

    public int getVote_count() {
        return vote_count;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public long getId() {
        return id;
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

    public String getOverview() {
        return overview;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getName() {
        return name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public String getType() {
        return type;
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

    public String getImdb_id() {
        return imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public List<Creator> getCreated_by() {
        return created_by;
    }

    public List<MovieDetailsResponse.Genre> getGenres() {
        return genres;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public List<MovieDetailsResponse.Language> getSpoken_languages() {
        return spoken_languages;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public List<MovieDetailsResponse.ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public List<MovieDetailsResponse.ProductionCountry> getProduction_countries() {
        return production_countries;
    }

    public MovieDetailsResponse.Collection getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public Episode getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public Episode getNext_episode_to_air() {
        return next_episode_to_air;
    }

    String original_name;
    String name;
    String first_air_date;
    String last_air_date;
    String type;
    String tagline;
    String status;
    String original_language;
    String imdb_id;
    String homepage;
    List<Creator> created_by;
    List<MovieDetailsResponse.Genre> genres;
    List<Season> seasons;
    List<MovieDetailsResponse.Language> spoken_languages;
    List<Network> networks;
    List<MovieDetailsResponse.ProductionCompany> production_companies;
    List<MovieDetailsResponse.ProductionCountry> production_countries;
    MovieDetailsResponse.Collection belongs_to_collection;
    Episode last_episode_to_air;
    Episode next_episode_to_air;

    public static class Creator implements Serializable {
        int gender;
        long id, credit_id;

        public int getGender() {
            return gender;
        }

        public long getId() {
            return id;
        }

        public long getCredit_id() {
            return credit_id;
        }

        public String getName() {
            return name;
        }

        public String getProfile_path() {
            return profile_path;
        }

        String name, profile_path;
    }


    public static class Episode implements Serializable {
        int season_number;
        float vote_average;
        long id, episode_number;
        String name;
        String overview;

        public int getSeason_number() {
            return season_number;
        }

        public float getVote_average() {
            return vote_average;
        }

        public long getId() {
            return id;
        }

        public long getEpisode_number() {
            return episode_number;
        }

        public String getName() {
            return name;
        }

        public String getOverview() {
            return overview;
        }

        public String getAir_date() {
            return air_date;
        }

        String air_date;

    }

    public static class Network implements Serializable {
        long id;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLogo_path() {
            return logo_path;
        }

        String name, logo_path;
    }

    public static class Season implements Serializable {
        int episode_count;
        long id;
        String air_date;
        String name;
        String overview;
        String poster_path;

        public int getEpisode_count() {
            return episode_count;
        }

        public long getId() {
            return id;
        }

        public String getAir_date() {
            return air_date;
        }

        public String getName() {
            return name;
        }

        public String getOverview() {
            return overview;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public String getSeason_number() {
            return season_number;
        }

        String season_number;
    }
}
