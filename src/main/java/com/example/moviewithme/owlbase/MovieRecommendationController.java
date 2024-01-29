package com.example.moviewithme.owlbase;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movie-recommendations")
public class MovieRecommendationController {

    // Endpoint to get movie recommendations
    @GetMapping
    public List<MovieRecommendation> getMovieRecommendations() {
        // SPARQL query string
        String sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX movie: <http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#>\n" +
                "# Kalkulujemy ważoną Jaccard Similarity\n" +
                "SELECT ?movie ?title ?weightedSimilarity WHERE {\n" +
                "  {\n" +
                "    SELECT ?movie (\n" +
                "        (\n" +
                "          (COUNT(DISTINCT ?commonGenre) * 0.2 + COUNT(DISTINCT ?commonActor) * 0.3 + COUNT(DISTINCT ?commonCountry) * 0.1 + COUNT(DISTINCT ?commonDirector) * 0.2 + COUNT(DISTINCT ?commonScreenwriter) * 0.2) /\n" +
                "          (COUNT(DISTINCT ?genre) * 0.2 + COUNT(DISTINCT ?actor) * 0.3 + COUNT(DISTINCT ?country) * 0.1 + COUNT(DISTINCT ?director) * 0.2 + COUNT(DISTINCT ?screenwriter) * 0.2 + 1)\n" +
                "        ) AS ?weightedSimilarity) WHERE {\n" +
                "      # Dodaj relacje między filmem a gatunkiem, aktorem, itp.\n" +
                "      ?movie movie:isGenreOf ?genre.\n" +
                "      ?movie movie:isActingBy ?actor.\n" +
                "      ?movie movie:isProducedBy ?country.\n" +
                "      ?movie movie:isDirectedBy ?director.\n" +
                "      ?movie movie:isSreenWritingBy ?screenwriter.\n" +
                "\n" +
                "      # Filtrujemy filmy, które mają co najmniej jeden taki gatunek, aktora, reżysera, scenarzystę lub kraj produkcji\n" +
                "      ?movie movie:isGenreOf ?commonGenre .\n" +
                "      ?commonGenre rdf:type movie:Genre .\n" +
                "\n" +
                "      ?movie movie:isActingBy ?commonActor .\n" +
                "      ?commonActor rdf:type movie:Actor .\n" +
                "\n" +
                "      ?movie movie:isProducedBy ?commonCountry .\n" +
                "      ?commonCountry rdf:type movie:MovieProductionCountry .\n" +
                "\n" +
                "      ?movie movie:isDirectedBy ?commonDirector .\n" +
                "      ?commonDirector rdf:type movie:Director .\n" +
                "\n" +
                "      ?movie movie:isSreenWritingBy ?commonScreenwriter .\n" +
                "      ?commonScreenwriter rdf:type movie:Screenwriter .\n" +
                "\n" +
                "      # Dodatkowe warunki filtrowania\n" +
                "      FILTER (?commonGenre = movie:genre35 || ?commonActor = movie:actor1125 || ?commonCountry = movie:countryGB || ?commonDirector = movie:director2294 || ?commonScreenwriter = movie:screenwriter8297)\n" +
                "    } GROUP BY ?movie\n" +
                "  }\n" +
                "  ?movie movie:hasTitle ?title .\n" +
                "}\n" +
                "ORDER BY DESC(?weightedSimilarity)\n" +
                "LIMIT 10000";

        // Create an empty list to store movie recommendations
        List<MovieRecommendation> movieRecommendations = new ArrayList<>();

        // Execute the SPARQL query and process the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://localhost:3030/#/dataset/movie/query", sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.next();
                String movieUri = solution.get("movie").toString();
                String title = solution.get("title").toString();
                double weightedSimilarity = solution.get("weightedSimilarity").asLiteral().getDouble();

                // Create a MovieRecommendation object and add it to the list
                MovieRecommendation movieRecommendation = new MovieRecommendation(movieUri, title, weightedSimilarity);
                movieRecommendations.add(movieRecommendation);
            }
        }

        return movieRecommendations;
    }
}
