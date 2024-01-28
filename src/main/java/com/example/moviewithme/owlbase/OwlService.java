package com.example.moviewithme.owlbase;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OwlService {

    private final OWLOntologyManager ontologyManager;
    private OWLOntology ontology;

    public OwlService() {
        this.ontologyManager = OWLManager.createOWLOntologyManager();

        try {
            this.ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("MovieWithMe.rdf"));
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
    public void addMovieIndividual(String movieId, String title) {
        // Tworzenie indywidualności
        OWLNamedIndividual movieIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + movieId));

        // Tworzenie typu dla indywidualności
        OWLClass movieClass = ontologyManager.getOWLDataFactory()
                .getOWLClass(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#Movie"));

        // Tworzenie data property
        OWLDataProperty hasTitleProperty = ontologyManager.getOWLDataFactory()
                .getOWLDataProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#hasTitle"));

        // Sprawdzenie, czy indywiduum o danym URI już istnieje
        if (!ontology.containsIndividualInSignature(movieIndividual.getIRI())) {
            // Dodawanie indywidualności do ontologii
            OWLAxiom declarationAxiom = ontologyManager.getOWLDataFactory().getOWLDeclarationAxiom(movieIndividual);
            OWLAxiom typeAxiom = ontologyManager.getOWLDataFactory().getOWLClassAssertionAxiom(movieClass, movieIndividual);
            OWLAxiom titleAxiom = ontologyManager.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(hasTitleProperty, movieIndividual, title);

            ontologyManager.addAxiom(ontology, declarationAxiom);
            ontologyManager.addAxiom(ontology, typeAxiom);
            ontologyManager.addAxiom(ontology, titleAxiom);

            System.out.println("Dodano indywiduum do ontologii: " + movieIndividual + " z hasTitle: " + title);
        } else {
            System.out.println("Indywiduum już istnieje w ontologii: " + movieIndividual);
        }
    }

    public void saveOntology() {
        try {
            ontologyManager.saveOntology(ontology, IRI.create(new File("MovieWithMe.rdf")));
            System.out.println("Ontologia została zapisana pomyślnie.");

            // Dodaj poniższy kod w celu weryfikacji zapisu
            File savedFile = new File("MovieWithMe.rdf");
            System.out.println("Czy plik istnieje po zapisie: " + savedFile.exists());
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }
}
