package com.example.moviewithme.movie;

public class Screenwriter {

    private Long screenwriterId;
    private String firstName;
    private String lastName;

    public Screenwriter() {
    }

    public Screenwriter(Long screenwriterId, String firstName, String lastName) {
        this.screenwriterId = screenwriterId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getScreenwriterId() {
        return screenwriterId;
    }

    public void setScreenwriterId(Long screenwriterId) {
        this.screenwriterId = screenwriterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
