package com.example.moviewithme.owlbase;

public class ActorResult {

    private String actorUri;
    private String label;

    public ActorResult(String actorUri, String label) {
        this.actorUri = actorUri;
        this.label = label;
    }

    public String getActorUri() {
        return actorUri;
    }

    public String getLabel() {
        return label;
    }
}
