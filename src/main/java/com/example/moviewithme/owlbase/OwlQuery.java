package com.example.moviewithme.owlbase;

import org.apache.jena.query.*;

public class OwlQuery {

    public static void main(String[] args) {
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                "SELECT ?movie ?title " +
                "WHERE {" +
                "  ?movie rdf:type owl:Movie ." +
                "  ?movie <http://example.org#hasTitle> ?title ." +
                "}";

        Query query = QueryFactory.create(queryString);

        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://localhost:3030/yourDataset/query", query)) {
            ResultSet results = queryExecution.execSelect();

            while (results.hasNext()) {
                QuerySolution solution = results.next();
                String movieUri = solution.getResource("movie").getURI();
                String title = solution.getLiteral("title").getString();
                System.out.println("Movie URI: " + movieUri + ", Title: " + title);
            }
        }
    }
}
