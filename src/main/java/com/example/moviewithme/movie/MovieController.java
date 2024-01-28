package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final OwlService owlService;
    private final MovieService movieService;

    @Autowired
    public MovieController(OwlService owlService, MovieService movieService) {
        this.owlService = owlService;
        this.movieService = movieService;
    }

    @GetMapping("/titles")
    public List<MovieApiResponse> getMoviesTitles() {
        List<String> imdbIds = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            imdbIds.add(String.valueOf(i));
        }
        return movieService.getMoviesForIds(imdbIds);
    }


    @PostMapping("/save")
    public String saveOntology() {
        owlService.saveOntology();
        return "Zapisano ontologię";
    }
}
