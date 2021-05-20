package com.app.tmdb.models;

import java.util.List;

public class MovieReviewsResponse {
    long id, total_results;
    int page, total_pages;
    List<Review> results;


    public long getId() {
        return id;
    }

    public long getTotal_results() {
        return total_results;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<Review> getResults() {
        return results;
    }

    //nested class
    public static class Review {
        String author, content, created_at, id, updated_at, url;
        Author author_details;

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getId() {
            return id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getUrl() {
            return url;
        }

        public Author getAuthor_details() {
            return author_details;
        }

        public static class Author {
            String name, username, avatar_path;
            float rating;

            public String getName() {
                return name;
            }

            public String getUsername() {
                return username;
            }

            public String getAvatar_path() {
                return avatar_path;
            }

            public float getRating() {
                return rating;
            }
        }

    }

}
