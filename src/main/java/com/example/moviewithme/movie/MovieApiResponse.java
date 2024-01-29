// MovieApiResponse.java
package com.example.moviewithme.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class MovieApiResponse {

    @JsonProperty("id")
    private int index; // Dodane pole "index"

    private String title;

    @JsonProperty("vote_average")
    private float voteAverage;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @JsonProperty("genres")
    private List<GenreId> genreIds;

    @JsonProperty("production_countries")
    private List<Country> productionCountries;

    @JsonProperty("credits")
    private Credits credits;

    private boolean isSeries;

    public boolean isSeries() {
        return isSeries;
    }

    public boolean setSeries(boolean series) {
        isSeries = series;
        return series;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<GenreId> getGenres() {
        return genreIds;
    }

    public void setGenres(List<GenreId> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Country> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<Country> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    // Klasa reprezentująca produkcję filmu w różnych krajach
    public static class Country {

        @JsonProperty("iso_3166_1")
        private String iso31661;

        @JsonProperty("name")
        private String name;

        public String getIso31661() {
            return iso31661;
        }

        public void setIso31661(String iso31661) {
            this.iso31661 = iso31661;
        }
    }

    // Klasa reprezentująca pole "credits"
    public static class Credits {

        private List<Cast> cast;
        private List<Crew> crew;

        public List<Cast> getCast() {
            return cast;
        }

        public void setCast(List<Cast> cast) {
            this.cast = cast;
        }

        public List<Crew> getCrew() {
            return crew;
        }

        public void setCrew(List<Crew> crew) {
            this.crew = crew;
        }
    }

    // Klasa reprezentująca elementy z tablicy "cast" w polu "credits"
    public static class Cast {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("known_for_department")
        private String job;

        public String getName() {
            return name;
        }

        public String getJob() {
            return job;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    // Klasa reprezentująca elementy z tablicy "crew" w polu "credits"
    public static class Crew {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("known_for_department")
        private String job;

        public String getName() {
            return name;
        }

        public String getJob() {
            return job;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
