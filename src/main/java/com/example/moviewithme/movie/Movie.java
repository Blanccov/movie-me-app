package com.example.moviewithme.movie;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Movie {

    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence"
    )
    private Long id;

    @Column(name = "movie_title")
    private String title;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "premiere")
    private LocalDate premiere;

    @Column(name = "is_series")
    private boolean isSeries;

    @Column(name = "rate")
    private float rate;

    public Movie() {
    }

    public Movie(Long id, String title, Long movieId, LocalDate premiere, boolean isSeries, float rate) {
        this.id = id;
        this.title = title;
        this.movieId = movieId;
        this.premiere = premiere;
        this.isSeries = isSeries;
        this.rate = rate;
    }

    public Movie(String title, Long movieId, LocalDate premiere, boolean isSeries, float rate) {
        this.title = title;
        this.movieId = movieId;
        this.premiere = premiere;
        this.isSeries = isSeries;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public LocalDate getPremiere() {
        return premiere;
    }

    public void setPremiere(LocalDate premiere) {
        this.premiere = premiere;
    }

    public boolean getSeries() {
        return isSeries;
    }

    public boolean setSeries(boolean series) {
        isSeries = series;
        return series;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", movieId=" + movieId +
                ", premiere=" + premiere +
                ", isSeries=" + isSeries +
                ", rate=" + rate +
                '}';
    }
}
