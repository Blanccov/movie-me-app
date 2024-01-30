package com.example.moviewithme.owlbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MovieQueryController {

    @Autowired
    private MovieQueryService movieQueryService;

    @GetMapping("/actors")
    public List<ActorResult> searchActorsByName(@RequestParam String name) {
        return movieQueryService.searchByTypeAndName("Actor", name);
    }

    @GetMapping("/directors")
    public List<ActorResult> getDirectorsByName(@RequestParam String name) {
        return movieQueryService.searchByTypeAndName("Director", name);
    }

    @GetMapping("/screenwriters")
    public List<ActorResult> getScreenwritersByName(@RequestParam String name) {
        return movieQueryService.searchByTypeAndName("Screenwriter", name);
    }

    @GetMapping("/countries")
    public List<ActorResult> getCountriesByMovie(@RequestParam String name) {
        return movieQueryService.searchByTypeAndName("MovieProductionCountry", name);
    }

    @GetMapping("/genres")
    public List<ActorResult> getGenresByMovie(@RequestParam String name) {
        return movieQueryService.searchByTypeAndName("Genre", name);
    }
}
