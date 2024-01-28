// MovieApiResponse.java

package com.example.moviewithme.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieApiResponse {

    @JsonProperty("Search")
    private List<MovieData> results;

    public List<MovieData> getResults() {
        return results;
    }

    public void setResults(List<MovieData> results) {
        this.results = results;
    }

    public static class MovieData {
        @JsonProperty("Title")
        private String originalTitle;

        @JsonProperty("imdbID")
        private String imdbID;

        // Gettery i settery

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public String getImdbID() {
            return imdbID;
        }

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }
    }
}
