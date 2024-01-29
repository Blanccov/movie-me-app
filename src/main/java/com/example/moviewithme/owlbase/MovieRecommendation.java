package com.example.moviewithme.owlbase;

public class MovieRecommendation {

    private String movieUri;
    private String title;
    private double weightedSimilarity;

    public MovieRecommendation(String movieUri, String title, double weightedSimilarity) {
        this.movieUri = movieUri;
        this.title = title;
        this.weightedSimilarity = weightedSimilarity;
    }

    public String getMovieUri() {
        return movieUri;
    }

    public void setMovieUri(String movieUri) {
        this.movieUri = movieUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWeightedSimilarity() {
        return weightedSimilarity;
    }

    public void setWeightedSimilarity(double weightedSimilarity) {
        this.weightedSimilarity = weightedSimilarity;
    }

    @Override
    public String toString() {
        return "MovieRecommendation{" +
                "movieUri='" + movieUri + '\'' +
                ", title='" + title + '\'' +
                ", weightedSimilarity=" + weightedSimilarity +
                '}';
    }
}
