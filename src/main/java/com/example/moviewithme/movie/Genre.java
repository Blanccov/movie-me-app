package com.example.moviewithme.movie;

public class Genre {
    private Long genreId;
    private String label;

    public Genre() {
    }

    public Genre(Long genreId, String label) {
        this.genreId = genreId;
        this.label = label;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
