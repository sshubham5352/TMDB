package com.app.tmdb.models;

import java.util.List;

public class MovieCreditsResponse {
    long id;
    List<Cast> cast;
    List<Crew> crew;

    public long getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    //nested class
    public static class Cast {
        long id;
        int gender, order, cast_id;
        String character, name, profile_path, credit_id;

        public long getId() {
            return id;
        }

        public int getGender() {
            return gender;
        }

        public int getOrder() {
            return order;
        }

        public int getCast_id() {
            return cast_id;
        }

        public String getCharacter() {
            return character;
        }

        public String getName() {
            return name;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public String getCredit_id() {
            return credit_id;
        }
    }

    //nested class
    public static class Crew {
        int id, gender;
        String credit_id, department, job, name, profile_path;

        public int getId() {
            return id;
        }

        public int getGender() {
            return gender;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public String getDepartment() {
            return department;
        }

        public String getJob() {
            return job;
        }

        public String getName() {
            return name;
        }

        public String getProfile_path() {
            return profile_path;
        }
    }


}
