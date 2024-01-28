package com.example.moviewithme.owlbase;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void addMovieIndividual(String movieId, String title, LocalDateTime premiere, boolean isSeries, float rate) {
        OWLNamedIndividual movieIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + movieId));

        OWLClass movieClass = ontologyManager.getOWLDataFactory()
                .getOWLClass(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#Movie"));

        OWLDataProperty hasTitleProperty = ontologyManager.getOWLDataFactory()
                .getOWLDataProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#hasTitle"));

        OWLDataProperty hasPremiereProperty = ontologyManager.getOWLDataFactory()
                .getOWLDataProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#hasPremiere"));

        OWLDataProperty isSeriesProperty = ontologyManager.getOWLDataFactory()
                .getOWLDataProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#isSeries"));

        OWLDataProperty hasRateProperty = ontologyManager.getOWLDataFactory()
                .getOWLDataProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#hasRate"));

        if (!ontology.containsIndividualInSignature(movieIndividual.getIRI())) {
            OWLAxiom declarationAxiom = ontologyManager.getOWLDataFactory().getOWLDeclarationAxiom(movieIndividual);
            OWLAxiom typeAxiom = ontologyManager.getOWLDataFactory().getOWLClassAssertionAxiom(movieClass, movieIndividual);
            OWLAxiom titleAxiom = ontologyManager.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(hasTitleProperty, movieIndividual, title);
            OWLAxiom premiereAxiom = ontologyManager.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(hasPremiereProperty, movieIndividual, formatDate(premiere));
            OWLAxiom isSeriesAxiom = ontologyManager.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(isSeriesProperty, movieIndividual, isSeries);
            OWLAxiom rateAxiom = ontologyManager.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(hasRateProperty, movieIndividual, rate);

            ontologyManager.addAxiom(ontology, declarationAxiom);
            ontologyManager.addAxiom(ontology, typeAxiom);
            ontologyManager.addAxiom(ontology, titleAxiom);
            ontologyManager.addAxiom(ontology, premiereAxiom);
            ontologyManager.addAxiom(ontology, isSeriesAxiom);
            ontologyManager.addAxiom(ontology, rateAxiom);

            System.out.println("Dodano indywiduum do ontologii: " + movieIndividual + " z hasTitle: " + title);
        } else {
            System.out.println("Indywiduum już istnieje w ontologii: " + movieIndividual);
        }
    }

    public void saveOntology() {
        try {
            ontologyManager.saveOntology(ontology, IRI.create(new File("MovieWithMe.rdf")));
            System.out.println("Ontologia została zapisana pomyślnie.");

            File savedFile = new File("MovieWithMe.rdf");
            System.out.println("Czy plik istnieje po zapisie: " + savedFile.exists());
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return dateTime.format(formatter);
    }
}
