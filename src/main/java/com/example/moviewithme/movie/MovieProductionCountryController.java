package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/production-countries")
public class MovieProductionCountryController {

    private final OwlService owlService;

    @Autowired
    public MovieProductionCountryController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping
    public String addProductionCountry(@RequestBody MovieProductionCountry country) {
        String ontologyId = "country" + country.getIso31661();

        owlService.addProductionCountry(ontologyId, country.getName());
        owlService.saveOntology();

        return "Dodano kraj produkcji: " + country.getName() + " z identyfikatorem: " + country.getIso31661();
    }

    @PostMapping("/{movieProductionCountryId}/produces/{movieId}")
    public String addProducingRelation(@PathVariable String movieProductionCountryId, @PathVariable String movieId) {
        String ontologyMovieId = "movie" + movieId;
        String ontologyProductionCountryId = "country" + movieProductionCountryId;

        owlService.addProducingRelation(ontologyMovieId, ontologyProductionCountryId);
        owlService.saveOntology();

        return "Dodano relację isProducedBy między filmem o identyfikatorze " + movieId +
                " a krajem produkcji o identyfikatorze " + movieProductionCountryId;
    }

}
