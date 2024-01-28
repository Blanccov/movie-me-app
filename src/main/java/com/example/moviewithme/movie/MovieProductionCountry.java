package com.example.moviewithme.movie;

public class MovieProductionCountry {
    private Long countryId;
    private String label;

    public MovieProductionCountry() {
    }

    public MovieProductionCountry(Long countryId, String label) {
        this.countryId = countryId;
        this.label = label;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
