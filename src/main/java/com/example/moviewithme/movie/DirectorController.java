package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    private final OwlService owlService;

    @Autowired
    public DirectorController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping
    public String addDirector(@RequestBody Director director) {
        String ontologyId = "director" + director.getDirectorId();
        owlService.addDirector(ontologyId, director.getFirstName(), director.getLastName());
        owlService.saveOntology();

        return "Dodano reżysera: " + director.getFirstName() + " " + director.getLastName() +
                " z identyfikatorem: " + director.getDirectorId();
    }
    @PostMapping("/{directorId}/directs/{movieId}")
    public String addDirectingRelation(@PathVariable String directorId, @PathVariable String movieId) {
        String director = "director" + directorId;
        String movie = "movie" + movieId;

        owlService.addDirectingRelation(movie, director);
        owlService.saveOntology();

        return "Dodano relację isDirectedBy między reżyserem o identyfikatorze " + directorId + " a filmem o identyfikatorze " + movieId;
    }
}
