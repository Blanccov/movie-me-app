package com.example.moviewithme.owlbase;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/movie-recommendations")
public class MovieRecommendationController {

    @GetMapping
    public List<MovieRecommendation> getMovieRecommendations(
            @RequestParam List<String> neighborFilter
    ) {
        String sparqlQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX movie: <http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#>\n" +
                "SELECT ?movie ?title ((COUNT(DISTINCT ?commonNeighbor) / (COUNT(DISTINCT ?actor) + COUNT(DISTINCT ?director) + COUNT(DISTINCT ?screenwriter) + COUNT(DISTINCT ?genre) + COUNT(DISTINCT ?country))) AS ?weightedSimilarity)\n" +
                "(GROUP_CONCAT(DISTINCT ?commonNeighborName; SEPARATOR=\", \") AS ?commonNeighbors)\n" +
                "WHERE {\n" +
                "   # Select the movie and its common neighbors\n" +
                "   ?movie movie:isActingBy ?actor.\n" +
                "   ?movie movie:isDirectedBy ?director.\n" +
                "   ?movie movie:isSreenWritingBy ?screenwriter.\n" +
                "   ?movie movie:isGenreOf ?genre.\n" +
                "   ?movie movie:isProducedBy ?country.\n" +
                "\n" +
                "   # Identify common neighbors\n" +
                "   {\n" +
                "      ?movie movie:isActingBy ?commonNeighbor .\n" +
                "      ?commonNeighbor rdf:type movie:Actor .\n" +
                "      ?commonNeighbor rdfs:label ?commonNeighborName.\n" +
                "   }\n" +
                "   UNION\n" +
                "   {\n" +
                "      ?movie movie:isDirectedBy ?commonNeighbor .\n" +
                "      ?commonNeighbor rdf:type movie:Director .\n" +
                "      ?commonNeighbor rdfs:label ?commonNeighborName.\n" +
                "   }\n" +
                "   UNION\n" +
                "   {\n" +
                "      ?movie movie:isSreenWritingBy ?commonNeighbor .\n" +
                "      ?commonNeighbor rdf:type movie:Screenwriter .\n" +
                "      ?commonNeighbor rdfs:label ?commonNeighborName.\n" +
                "   }\n" +
                "   UNION\n" +
                "   {\n" +
                "      ?movie movie:isGenreOf ?commonNeighbor .\n" +
                "      ?commonNeighbor rdf:type movie:Genre .\n" +
                "      ?commonNeighbor rdfs:label ?commonNeighborName.\n" +
                "   }\n" +
                "   UNION\n" +
                "   {\n" +
                "      ?movie movie:isProducedBy ?commonNeighbor .\n" +
                "      ?commonNeighbor rdf:type movie:MovieProductionCountry .\n" +
                "      ?commonNeighbor rdfs:label ?commonNeighborName.\n" +
                "   }\n" +
                "\n" +
                "   # Additional filtering conditions\n" +
                "   FILTER (\n";

        for (String filter : neighborFilter) {
            sparqlQuery += "      ?commonNeighbor = movie:" + filter + " || ";
        }

        sparqlQuery = sparqlQuery.substring(0, sparqlQuery.length() - 4) + "\n   )\n" +
                "  ?movie movie:hasTitle ?title .\n" +
                "}\n" +
                "\n" +
                "GROUP BY ?movie ?title\n" +
                "ORDER BY DESC(?weightedSimilarity)\n" +
                "LIMIT 10";

        List<MovieRecommendation> movieRecommendations = new ArrayList<>();

        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://localhost:3030/test4/query", sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.next();
                String movieUri = solution.get("movie").toString();
                String title = solution.get("title").toString();
                double weightedSimilarity = solution.get("weightedSimilarity").asLiteral().getDouble();

                MovieRecommendation movieRecommendation = new MovieRecommendation(movieUri, title, weightedSimilarity);
                movieRecommendations.add(movieRecommendation);
            }
        }

        return movieRecommendations;
    }
}
