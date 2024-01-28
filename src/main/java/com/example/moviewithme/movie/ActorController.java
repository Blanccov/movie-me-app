package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        owlService.saveOntology();

        return "Dodano aktora: " + actor.getFirstName() + " " + actor.getLastName() + " z identyfikatorem: " + ontologyId;
    }

    @PostMapping("/{actorId}/actIn/{movieId}")
    public String addActingRelation(@PathVariable String actorId, @PathVariable String movieId) {
        String actor = "actor" + actorId;
        String movie = "movie" + movieId;

        owlService.addActingRelation(movie, actor);
        owlService.saveOntology();

        return "Dodano relację isActingBy między aktorem o identyfikatorze " + actorId + " a filmem o identyfikatorze " + movieId;
    }
}
