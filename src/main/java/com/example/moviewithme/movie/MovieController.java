package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        List<MovieApiResponse> movieList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            String imdbId = String.valueOf(i);
            List<MovieApiResponse> moviesForId = movieService.getMoviesForIds(Collections.singletonList(imdbId));

            if (!moviesForId.isEmpty()) {
                MovieApiResponse movieApiResponse = moviesForId.get(0);

                String ontologyId = "movie" + movieApiResponse.getIndex();
                owlService.addMovieIndividual(
                        ontologyId,
                        movieApiResponse.getTitle(),
                        movieApiResponse.getReleaseDate(),
                        movieApiResponse.isSeries(),
                        movieApiResponse.getVoteAverage()
                );

                // Dodaj reżysera, jeśli istnieje
                List<MovieApiResponse.Crew> crewList = movieApiResponse.getCredits().getCrew();
                if (crewList != null) {
                    for (MovieApiResponse.Crew crewMember : crewList) {
                        if ("Directing".equals(crewMember.getJob())) {
                            String directorOntologyId = "director" + crewMember.getId();
                            owlService.addDirector(directorOntologyId, crewMember.getName());
                            owlService.addDirectingRelation(ontologyId, directorOntologyId);
                        }
                        if ("Writing".equals(crewMember.getJob())) {
                            String screenwriterOntologyId = "screenwriter" + crewMember.getId();
                            owlService.addScreenwriter(screenwriterOntologyId, crewMember.getName());
                            owlService.addWritingRelation(ontologyId, screenwriterOntologyId);
                        }
                    }
                }

                List<MovieApiResponse.Cast> castList = movieApiResponse.getCredits().getCast();
                if (castList != null) {
                    for (MovieApiResponse.Cast crewMember : castList) {
                        if ("Acting".equals(crewMember.getJob())) {
                            String actorOntologyId = "actor" + crewMember.getId();
                            owlService.addActor(actorOntologyId, crewMember.getName());
                            owlService.addActingRelation(ontologyId, actorOntologyId);
                        }
                    }
                }
                saveOntology();
                movieList.add(movieApiResponse);
            }
        }

        return movieList;
    }



    @PostMapping("/save")
    public String saveOntology() {
        owlService.saveOntology();
        return "Zapisano ontologię";
    }
    @PostMapping
    public String addMovie(@RequestBody MovieApiResponse movieApiResponse) {
        String ontologyId = "movie" + movieApiResponse.getIndex();
        owlService.addMovieIndividual(
                ontologyId,
                movieApiResponse.getTitle(),
                movieApiResponse.getReleaseDate(),
                movieApiResponse.setSeries(false),
                movieApiResponse.getVoteAverage()
        );
        return "Dodano film: " + movieApiResponse.getTitle();
    }
}
