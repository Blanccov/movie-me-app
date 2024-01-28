package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GenreController {

    private final OwlService owlService;

    @Autowired
    public GenreController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping("/genres")
    public String addGenre(@RequestBody Genre genre) {
        String ontologyId = "genre" + genre.getGenreId();

        owlService.addGenre(ontologyId, genre.getLabel());
        owlService.saveOntology();

        return "Dodano gatunek: " + genre.getLabel() + " z identyfikatorem: " + genre.getGenreId();
    }
    
}
