package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final OwlService owlService;

    @Autowired
    public ActorController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping
    public String addActor(@RequestBody Actor actor) {
        String ontologyId = "actor" + actor.getActorId();

        owlService.addActor(ontologyId, actor.getFirstName(), actor.getLastName());
        return "Dodano aktora: " + actor.getFirstName() + " " + actor.getLastName() + " z identyfikatorem: " + ontologyId;
    }
}
