package com.example.moviewithme.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Genre {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public Genre() {
    }

    public String getName() {
        return name;
    }

    public Genre(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
