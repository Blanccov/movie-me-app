package com.example.moviewithme.owlbase;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/selected-movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieRecommendationPairController {

    @GetMapping
    public Map<String, Object> getSelectedMoviesWithCommonRelations(
            @RequestParam List<String> selectedMovies
    ) {
        String endpoint = "http://localhost:3030/test4/query";

        StringBuilder sparqlQuery = new StringBuilder("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX movie: <http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#>\n" +
                "\n" +
                "SELECT ?selectedMovie ?title ?commonRelationsCount\n" +
                "WHERE {\n" +
                "  VALUES ?selectedMovie { ");

        for (String movie : selectedMovies) {
            sparqlQuery.append("movie:").append(movie).append(" ");
        }

        sparqlQuery.append("}\n" +
                "\n" +
                "  {\n" +
                "    SELECT ?selectedMovie (COUNT(?commonRelation) as ?commonRelationsCount)\n" +
                "    WHERE {\n" +
                "      VALUES ?compareMovie { ");

        for (String movie : selectedMovies) {
            sparqlQuery.append("movie:").append(movie).append(" ");
        }

        sparqlQuery.append("}\n" +
                "      FILTER (?selectedMovie != ?compareMovie)\n" +
                "\n" +
                "      {\n" +
                "        SELECT DISTINCT ?selectedMovie ?commonRelation\n" +
                "        WHERE {\n" +
                "          VALUES ?selectedMovie { ");

        for (String movie : selectedMovies) {
            sparqlQuery.append("movie:").append(movie).append(" ");
        }

        sparqlQuery.append("}\n" +
                "          VALUES ?compareMovie { ");

        for (String movie : selectedMovies) {
            sparqlQuery.append("movie:").append(movie).append(" ");
        }

        sparqlQuery.append("}\n" +
                "          FILTER (?selectedMovie != ?compareMovie)\n" +
                "\n" +
                "          ?selectedMovie ?relation ?commonRelation.\n" +
                "          ?compareMovie ?relation ?commonRelation.\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "    GROUP BY ?selectedMovie\n" +
                "  }\n" +
                "\n" +
                "  ?selectedMovie movie:hasTitle ?title.\n" +
                "}\n" +
                "ORDER BY DESC(?commonRelationsCount)\n" +
                "LIMIT 1");

        Map<String, Object> selectedMoviesWithCommonRelations = new HashMap<>();

        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(endpoint, sparqlQuery.toString())) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.next();
                String selectedMovie = solution.get("selectedMovie").toString();
                String title = solution.get("title").toString();
                int commonRelationsCount = solution.get("commonRelationsCount").asLiteral().getInt();

                selectedMoviesWithCommonRelations.put("selectedMovie", selectedMovie);
                selectedMoviesWithCommonRelations.put("title", title);
                selectedMoviesWithCommonRelations.put("commonRelationsCount", commonRelationsCount);
            }
        }

        return selectedMoviesWithCommonRelations;
    }
}
