package com.example.moviewithme.owlbase;

import com.example.moviewithme.owlbase.ActorResult;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieQueryService {

    private static final String PREFIXES = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX movie: <http://www.semanticweb.org/dmade/ontologies/2024/0/MovieWithMe#>\n";

    public List<ActorResult> searchByTypeAndName(String type, String name) {
        String sparqlQuery = PREFIXES +
                "SELECT ?actor ?label " +
                "WHERE {" +
                "  ?actor rdf:type movie:" + type + " ;" +
                "         rdfs:label ?label ." +
                "  FILTER regex(?label, \"" + name + "\", \"i\")" +  // Nieczułe na wielkość liter
                "}";

        return executeQueryForActors(sparqlQuery);
    }

    private List<ActorResult> executeQueryForActors(String sparqlQuery) {
        List<ActorResult> results = new ArrayList<>();

        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://localhost:3030/test4/query", sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.next();
                String actorUri = getShortUri(solution.get("actor").toString());
                String label = solution.get("label").toString();
                results.add(new ActorResult(actorUri, label));
            }
        }

        return results;
    }

    private String getShortUri(String uri) {
        return uri.substring(uri.lastIndexOf("#") + 1);
    }
}
