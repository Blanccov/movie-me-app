package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final OwlService owlService;

    public MovieController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping("/add/{movieId}")
    public ResponseEntity<String> addMovie(@PathVariable String movieId) {
        owlService.addMovieIndividual(movieId);
        owlService.saveOntology();
        return ResponseEntity.ok("Movie added successfully.");
    }
}

