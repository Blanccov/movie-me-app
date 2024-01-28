// Kontroler
package com.example.moviewithme.movie;

import com.example.moviewithme.owlbase.OwlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/screenwriters")
public class ScreenwriterController {

    private final OwlService owlService;

    @Autowired
    public ScreenwriterController(OwlService owlService) {
        this.owlService = owlService;
    }

    @PostMapping
    public String addScreenwriter(@RequestBody Screenwriter screenwriter) {
        String ontologyId = "screenwriter" + screenwriter.getScreenwriterId();

        owlService.addScreenwriter(ontologyId, screenwriter.getFirstName(), screenwriter.getLastName());
        owlService.saveOntology();

        return "Dodano scenarzystę: " + screenwriter.getFirstName() + " " + screenwriter.getLastName()
                + " z identyfikatorem: " + screenwriter.getScreenwriterId();
    }

    @PostMapping("/{screenwriterId}/writes/{movieId}")
    public String addWritingRelation(@PathVariable String screenwriterId, @PathVariable String movieId) {
        String screenwriter = "screenwriter" + screenwriterId;
        String movie = "movie" + movieId;

        owlService.addWritingRelation(movie, screenwriter);
        owlService.saveOntology();

        return "Dodano relację isWrittenBy między scenarzystą o identyfikatorze " + screenwriterId + " a filmem o identyfikatorze " + movieId;
    }
}
