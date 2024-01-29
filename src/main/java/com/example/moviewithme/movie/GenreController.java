package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final OwlService owlService;

    @Autowired
    public GenreController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping()
    public String addGenre(@RequestBody Genre genre) {
        String ontologyId = "genre" + genre.getId();

        owlService.addGenre(ontologyId, genre.getName());
        owlService.saveOntology();

        return "Dodano gatunek: " + genre.getName() + " z identyfikatorem: " + genre.getId();
    }

    @PostMapping("/{genreId}/isGenreOf/{movieId}")
    public String addGenreRelation(@PathVariable String genreId, @PathVariable String movieId) {
        String ontologyMovieId = "movie" + movieId;
        String ontologyGenreId = "genre" + genreId;

        owlService.addGenreRelation(ontologyMovieId, ontologyGenreId);
        owlService.saveOntology();

        return "Dodano relację isGenreOf między filmem o identyfikatorze " + movieId +
                " a gatunkiem o identyfikatorze " + genreId;
    }
}
