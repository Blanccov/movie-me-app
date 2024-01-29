package com.example.moviewithme.owlbase;

import org.apache.jena.query.*;

public class OwlQuery {

    public static void main(String[] args) {
        // Łańcuch zapytania SPARQL
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                "SELECT ?movie ?title " +
                "WHERE {" +
                "  ?movie rdf:type owl:Movie ." +
                "  ?movie <http://example.org#hasTitle> ?title ." +
                // Dodaj więcej warunków zależnie od Twoich potrzeb
                "}";

        // Utwórz zapytanie SPARQL
        Query query = QueryFactory.create(queryString);

        // Wykonaj zapytanie SPARQL na dowolnej bazie danych SPARQL (np. Apache Jena Fuseki)
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://localhost:3030/yourDataset/query", query)) {
            ResultSet results = queryExecution.execSelect();

            while (results.hasNext()) {
                QuerySolution solution = results.next();
                // Pobierz wyniki zapytania i obsłuż je
                String movieUri = solution.getResource("movie").getURI();
                String title = solution.getLiteral("title").getString();
                System.out.println("Movie URI: " + movieUri + ", Title: " + title);
            }
        }
    }
}
