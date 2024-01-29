// GenreId.java
package com.example.moviewithme.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenreId {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public GenreId() {
    }

    public GenreId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
