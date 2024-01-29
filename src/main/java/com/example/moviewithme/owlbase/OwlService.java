package com.example.moviewithme.owlbase;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
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

    public void addMovieIndividual(String movieId, String title, LocalDate premiere, boolean isSeries, float rate) {
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

    public void addActor(String actorId, String name) {
        OWLNamedIndividual actorIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + actorId));

        OWLClass actorClass = ontologyManager.getOWLDataFactory()
                .getOWLClass(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#Actor"));

        OWLAnnotationProperty labelProperty = ontologyManager.getOWLDataFactory()
                .getOWLAnnotationProperty(IRI.create("http://www.w3.org/2000/01/rdf-schema#label"));

        if (!ontology.containsIndividualInSignature(actorIndividual.getIRI())) {
            OWLAxiom declarationAxiom = ontologyManager.getOWLDataFactory().getOWLDeclarationAxiom(actorIndividual);
            OWLAxiom typeAxiom = ontologyManager.getOWLDataFactory().getOWLClassAssertionAxiom(actorClass, actorIndividual);
            ;
            OWLAnnotation labelAnnotation = ontologyManager.getOWLDataFactory().getOWLAnnotation(labelProperty, ontologyManager.getOWLDataFactory().getOWLLiteral(name));
            OWLAxiom labelAxiom = ontologyManager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(actorIndividual.getIRI(), labelAnnotation);

            ontologyManager.addAxiom(ontology, declarationAxiom);
            ontologyManager.addAxiom(ontology, typeAxiom);
            ontologyManager.addAxiom(ontology, labelAxiom);

            System.out.println("Dodano indywiduum do ontologii: " + actorIndividual + " z rdfs:label: " + name);
        } else {
            System.out.println("Indywiduum aktora już istnieje w ontologii: " + actorIndividual);
        }
    }

    public void addGenre(String genreId, String label) {
        OWLNamedIndividual genreIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + genreId));

        OWLClass genreClass = ontologyManager.getOWLDataFactory()
                .getOWLClass(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#Genre"));

        OWLAnnotationProperty labelProperty = ontologyManager.getOWLDataFactory()
                .getOWLAnnotationProperty(IRI.create("http://www.w3.org/2000/01/rdf-schema#label"));

        if (!ontology.containsIndividualInSignature(genreIndividual.getIRI())) {
            OWLAxiom declarationAxiom = ontologyManager.getOWLDataFactory().getOWLDeclarationAxiom(genreIndividual);
            OWLAxiom typeAxiom = ontologyManager.getOWLDataFactory().getOWLClassAssertionAxiom(genreClass, genreIndividual);

            OWLAnnotation labelAnnotation = ontologyManager.getOWLDataFactory().getOWLAnnotation(labelProperty, ontologyManager.getOWLDataFactory().getOWLLiteral(label));
            OWLAxiom labelAxiom = ontologyManager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(genreIndividual.getIRI(), labelAnnotation);

            ontologyManager.addAxiom(ontology, declarationAxiom);
            ontologyManager.addAxiom(ontology, typeAxiom);
            ontologyManager.addAxiom(ontology, labelAxiom);

            System.out.println("Dodano indywiduum do ontologii: " + genreIndividual + " z rdfs:label: " + label);
        } else {
            System.out.println("Indywiduum gatunku już istnieje w ontologii: " + genreIndividual);
        }
    }

    public void addScreenwriter(String screenwriterId, String name) {
        OWLNamedIndividual screenwriterIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + screenwriterId));

        OWLClass screenwriterClass = ontologyManager.getOWLDataFactory()
                .getOWLClass(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#Screenwriter"));

        OWLAnnotationProperty labelProperty = ontologyManager.getOWLDataFactory()
                .getOWLAnnotationProperty(IRI.create("http://www.w3.org/2000/01/rdf-schema#label"));

        if (!ontology.containsIndividualInSignature(screenwriterIndividual.getIRI())) {
            OWLAxiom declarationAxiom = ontologyManager.getOWLDataFactory().getOWLDeclarationAxiom(screenwriterIndividual);
            OWLAxiom typeAxiom = ontologyManager.getOWLDataFactory().getOWLClassAssertionAxiom(screenwriterClass, screenwriterIndividual);

            OWLAnnotation labelAnnotation = ontologyManager.getOWLDataFactory().getOWLAnnotation(labelProperty, ontologyManager.getOWLDataFactory().getOWLLiteral(name));
            OWLAxiom labelAxiom = ontologyManager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(screenwriterIndividual.getIRI(), labelAnnotation);

            ontologyManager.addAxiom(ontology, declarationAxiom);
            ontologyManager.addAxiom(ontology, typeAxiom);
            ontologyManager.addAxiom(ontology, labelAxiom);

            System.out.println("Dodano indywiduum do ontologii: " + screenwriterIndividual + " z rdfs:label: " + name);
        } else {
            System.out.println("Indywiduum scenarzysty już istnieje w ontologii: " + screenwriterIndividual);
        }
    }
    public void addProductionCountry(String countryId, String label) {
        OWLNamedIndividual countryIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + countryId));

        OWLClass countryClass = ontologyManager.getOWLDataFactory()
                .getOWLClass(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#MovieProductionCountry"));

        OWLAnnotationProperty labelProperty = ontologyManager.getOWLDataFactory()
                .getOWLAnnotationProperty(IRI.create("http://www.w3.org/2000/01/rdf-schema#label"));

        if (!ontology.containsIndividualInSignature(countryIndividual.getIRI())) {
            OWLAxiom declarationAxiom = ontologyManager.getOWLDataFactory().getOWLDeclarationAxiom(countryIndividual);
            OWLAxiom typeAxiom = ontologyManager.getOWLDataFactory().getOWLClassAssertionAxiom(countryClass, countryIndividual);

            OWLAnnotation labelAnnotation = ontologyManager.getOWLDataFactory().getOWLAnnotation(labelProperty, ontologyManager.getOWLDataFactory().getOWLLiteral(label));
            OWLAxiom labelAxiom = ontologyManager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(countryIndividual.getIRI(), labelAnnotation);

            ontologyManager.addAxiom(ontology, declarationAxiom);
            ontologyManager.addAxiom(ontology, typeAxiom);
            ontologyManager.addAxiom(ontology, labelAxiom);

            System.out.println("Dodano indywiduum do ontologii: " + countryIndividual + " z rdfs:label: " + label);
        } else {
            System.out.println("Indywiduum kraju produkcji już istnieje w ontologii: " + countryIndividual);
        }
    }

    public void addDirector(String directorId, String name) {
        OWLNamedIndividual directorIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + directorId));

        OWLClass directorClass = ontologyManager.getOWLDataFactory()
                .getOWLClass(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#Director"));

        OWLAnnotationProperty labelProperty = ontologyManager.getOWLDataFactory()
                .getOWLAnnotationProperty(IRI.create("http://www.w3.org/2000/01/rdf-schema#label"));

        if (!ontology.containsIndividualInSignature(directorIndividual.getIRI())) {
            OWLAxiom declarationAxiom = ontologyManager.getOWLDataFactory().getOWLDeclarationAxiom(directorIndividual);
            OWLAxiom typeAxiom = ontologyManager.getOWLDataFactory().getOWLClassAssertionAxiom(directorClass, directorIndividual);

            OWLAnnotation labelAnnotation = ontologyManager.getOWLDataFactory().getOWLAnnotation(labelProperty, ontologyManager.getOWLDataFactory().getOWLLiteral(name));
            OWLAxiom labelAxiom = ontologyManager.getOWLDataFactory().getOWLAnnotationAssertionAxiom(directorIndividual.getIRI(), labelAnnotation);

            ontologyManager.addAxiom(ontology, declarationAxiom);
            ontologyManager.addAxiom(ontology, typeAxiom);
            ontologyManager.addAxiom(ontology, labelAxiom);

            System.out.println("Dodano indywiduum do ontologii: " + directorIndividual + " z rdfs:label: " + name);
        } else {
            System.out.println("Indywiduum reżysera już istnieje w ontologii: " + directorIndividual);
        }
    }

    public void addActingRelation(String movieId, String actorId) {
        OWLNamedIndividual movieIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + movieId));

        OWLNamedIndividual actorIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + actorId));

        OWLObjectProperty isActingByProperty = ontologyManager.getOWLDataFactory()
                .getOWLObjectProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#isActingBy"));

        // Sprawdzamy, czy indywidualne istnieją w ontologii
        if (ontology.containsIndividualInSignature(movieIndividual.getIRI()) && ontology.containsIndividualInSignature(actorIndividual.getIRI())) {
            // Tworzymy relację między filmem a aktorem
            OWLAxiom actingAxiom = ontologyManager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(isActingByProperty, movieIndividual, actorIndividual);
            ontologyManager.addAxiom(ontology, actingAxiom);

            System.out.println("Dodano relację isActingBy między filmem " + movieIndividual + " a aktorem " + actorIndividual);
        } else {
            System.out.println("Nie można dodać relacji isActingBy, ponieważ film lub aktor nie istnieje w ontologii.");
        }
    }

    public void addDirectingRelation(String movieId, String directorId) {
        OWLNamedIndividual movieIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + movieId));

        OWLNamedIndividual directorIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + directorId));

        OWLObjectProperty isDirectedByProperty = ontologyManager.getOWLDataFactory()
                .getOWLObjectProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#isDirectedBy"));

        // Sprawdzamy, czy indywidualne istnieją w ontologii
        if (ontology.containsIndividualInSignature(movieIndividual.getIRI()) && ontology.containsIndividualInSignature(directorIndividual.getIRI())) {
            // Tworzymy relację między filmem a reżyserem
            OWLAxiom directingAxiom = ontologyManager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(isDirectedByProperty, movieIndividual, directorIndividual);
            ontologyManager.addAxiom(ontology, directingAxiom);

            System.out.println("Dodano relację isDirectedBy między filmem " + movieIndividual + " a reżyserem " + directorIndividual);
        } else {
            System.out.println("Nie można dodać relacji isDirectedBy, ponieważ film lub reżyser nie istnieje w ontologii.");
        }
    }
    public void addWritingRelation(String movieId, String screenwriterId) {
        OWLNamedIndividual movieIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + movieId));

        OWLNamedIndividual screenwriterIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + screenwriterId));

        OWLObjectProperty isWrittenByProperty = ontologyManager.getOWLDataFactory()
                .getOWLObjectProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#isSreenWritingBy"));

        // Sprawdzamy, czy indywidualne istnieją w ontologii
        if (ontology.containsIndividualInSignature(movieIndividual.getIRI()) && ontology.containsIndividualInSignature(screenwriterIndividual.getIRI())) {
            // Tworzymy relację między filmem a scenarzystą
            OWLAxiom writingAxiom = ontologyManager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(isWrittenByProperty, movieIndividual, screenwriterIndividual);
            ontologyManager.addAxiom(ontology, writingAxiom);

            System.out.println("Dodano relację isScreenwritingBy między filmem " + movieIndividual + " a scenarzystą " + screenwriterIndividual);
        } else {
            System.out.println("Nie można dodać relacji isScreenwritingBy, ponieważ film lub scenarzysta nie istnieje w ontologii.");
        }
    }

    public void addProducingRelation(String movieId, String countryId) {
        OWLNamedIndividual movieIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + movieId));

        OWLNamedIndividual countryIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + countryId));

        OWLObjectProperty isProducedByProperty = ontologyManager.getOWLDataFactory()
                .getOWLObjectProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#isProducedBy"));

        // Sprawdzamy, czy indywidualne istnieją w ontologii
        if (ontology.containsIndividualInSignature(movieIndividual.getIRI()) && ontology.containsIndividualInSignature(countryIndividual.getIRI())) {
            // Tworzymy relację między filmem a krajem produkcji
            OWLAxiom producingAxiom = ontologyManager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(isProducedByProperty, movieIndividual, countryIndividual);
            ontologyManager.addAxiom(ontology, producingAxiom);

            System.out.println("Dodano relację isProducedBy między filmem " + movieIndividual + " a krajem " + countryIndividual);
        } else {
            System.out.println("Nie można dodać relacji isProducedBy, ponieważ film lub kraj produkcji nie istnieje w ontologii.");
        }
    }
    public void addGenreRelation(String movieId, String genreId) {
        OWLNamedIndividual movieIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + movieId));

        OWLNamedIndividual genreIndividual = ontologyManager.getOWLDataFactory()
                .getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#" + genreId));

        OWLObjectProperty isGenreOfProperty = ontologyManager.getOWLDataFactory()
                .getOWLObjectProperty(IRI.create("http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#isGenreOf"));

        // Sprawdzamy, czy indywidualne istnieją w ontologii
        if (ontology.containsIndividualInSignature(movieIndividual.getIRI()) && ontology.containsIndividualInSignature(genreIndividual.getIRI())) {
            // Tworzymy relację między filmem a gatunkiem
            OWLAxiom genreAxiom = ontologyManager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(isGenreOfProperty, movieIndividual, genreIndividual);
            ontologyManager.addAxiom(ontology, genreAxiom);

            System.out.println("Dodano relację isGenreOf między filmem " + movieIndividual + " a gatunkiem " + genreIndividual);
        } else {
            System.out.println("Nie można dodać relacji isGenreOf, ponieważ film lub gatunek nie istnieje w ontologii.");
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

    private OWLLiteral formatDate(LocalDate date) {
        LocalDateTime dateTime = date.atStartOfDay(); // Dodane: Ustawienie godziny na północ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        return ontologyManager.getOWLDataFactory().getOWLLiteral(formattedDate, OWL2Datatype.XSD_DATE_TIME);
    }

}
