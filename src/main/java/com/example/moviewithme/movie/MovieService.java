// MovieService.java
package com.example.moviewithme.movie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final String tmdbApiUrl = "https://api.themoviedb.org/3/movie/";
    private final RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MovieApiResponse> getMoviesForIds(List<String> movieIds) {
        List<MovieApiResponse> movieResponses = new ArrayList<>();
        int successfulRequests = 0;

        for (String movieId : movieIds) {
            String url = tmdbApiUrl + movieId + "?api_key=" + apiKey + "&append_to_response=credits&original_language=en";

            try {
                // Pobierz dane z TMDB API przy użyciu restTemplate
                MovieApiResponse movieResponse = restTemplate.getForObject(url, MovieApiResponse.class);

                if (movieResponse != null && "en".equals(movieResponse.getOriginalLanguage())) {
                    movieResponses.add(movieResponse);
                    successfulRequests++;
                } else if (movieResponse != null) {
                    // Film nie jest w języku angielskim, ale nie wypisuj błędu
                    System.out.println("Film o ID " + movieId + " nie jest w języku angielskim.");
                } else {
                    System.out.println("Nie udało się pobrać informacji o filmie z TMDB API. ID: " + movieId);
                }
            } catch (HttpClientErrorException.NotFound e) {
                System.out.println("Film o ID " + movieId + " nie został znaleziony.");
            } catch (Exception e) {
                System.out.println("Wystąpił błąd podczas pobierania danych z TMDB API. ID: " + movieId);
                e.printStackTrace();
            }
        }
        System.out.println("Liczba udanych zapytań: " + successfulRequests);
        return movieResponses;
    }
}
