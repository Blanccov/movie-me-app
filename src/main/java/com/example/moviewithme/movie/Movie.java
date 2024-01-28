package com.example.moviewithme.movie;

import jakarta.persistence.*;

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
    private Long movieId; // Nowa zmienna movieId

    public Movie() {
    }

    public Movie(Long id, String title, Long movieId) {
        this.id = id;
        this.title = title;
        this.movieId = movieId;
    }

    public Movie(String title, Long movieId) {
        this.title = title;
        this.movieId = movieId;
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", movieId=" + movieId +
                '}';
    }
}
