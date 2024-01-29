package com.example.moviewithme.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieProductionCountry {
    @JsonProperty("iso_3166_1")
    private String iso31661;

    @JsonProperty("name")
    private String name;

    public String getIso31661() {
        return iso31661;
    }

    public String getName() {
        return name;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }
}
