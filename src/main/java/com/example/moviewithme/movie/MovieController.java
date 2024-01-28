package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final OwlService owlService;

    @Autowired
    public MovieController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping
    public String addMovie(@RequestBody Movie movie) {
        String ontologyId = "movie" + movie.getMovieId();
        owlService.addMovieIndividual(
                ontologyId,
                movie.getTitle(),
                movie.getPremiere(),
                movie.getSeries(),
                movie.getRate()
        );
        return "Dodano film: " + movie.getTitle();
    }

    @PostMapping("/save")
    public String saveOntology() {
        owlService.saveOntology();
        return "Zapisano ontologię";
    }
}
