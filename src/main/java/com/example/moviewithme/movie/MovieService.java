// MovieService.java
package com.example.moviewithme.movie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final String tmdbApiUrl = "https://api.themoviedb.org/3/movie/";
    private final RestTemplate restTemplate;

    private int successfulRequestsCounter = 0;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MovieApiResponse> getMoviesForIds(List<String> movieIds) {
        List<MovieApiResponse> movieResponses = new ArrayList<>();

        for (String movieId : movieIds) {
            if (successfulRequestsCounter >= 500) {
                // Zatrzymaj kod po wykonaniu 2 prawidłowych żądań
                break;
            }

            String url = tmdbApiUrl + movieId + "?api_key=" + apiKey + "&append_to_response=credits";

            try {
                // Retrieve data from TMDB API using restTemplate
                MovieApiResponse movieResponse = restTemplate.getForObject(url, MovieApiResponse.class);

                if (movieResponse != null && "en".equals(movieResponse.getOriginalLanguage()) && movieResponse.getReleaseDate() != null && movieResponse.getReleaseDate().isAfter(LocalDate.of(2010, 1, 1))) {
                    // Limit the number of cast members to the first 5
                    if (movieResponse.getCredits() != null && movieResponse.getCredits().getCast() != null) {
                        movieResponse.getCredits().setCast(movieResponse.getCredits().getCast().subList(0, Math.min(5, movieResponse.getCredits().getCast().size())));
                    }

                    // Filter and limit the crew members based on known_for_department
                    if (movieResponse.getCredits() != null && movieResponse.getCredits().getCrew() != null) {
                        List<MovieApiResponse.Crew> directingCrew = movieResponse.getCredits().getCrew().stream()
                                .filter(crew -> "Directing".equals(crew.getJob()))
                                .limit(3)
                                .collect(Collectors.toList());

                        List<MovieApiResponse.Crew> writingCrew = movieResponse.getCredits().getCrew().stream()
                                .filter(crew -> "Writing".equals(crew.getJob()))
                                .limit(2)
                                .collect(Collectors.toList());

                        // Combine the filtered crew lists
                        List<MovieApiResponse.Crew> combinedCrew = new ArrayList<>();
                        combinedCrew.addAll(directingCrew);
                        combinedCrew.addAll(writingCrew);

                        movieResponse.getCredits().setCrew(combinedCrew);
                    }

                    movieResponses.add(movieResponse);
                    successfulRequestsCounter++; // Zwiększ licznik dla prawidłowych żądań
                } else if (movieResponse != null) {
                    System.out.println("Movie with ID " + movieId + " is not in English.");
                } else {
                    System.out.println("Failed to retrieve movie information from TMDB API. ID: " + movieId);
                }
            } catch (HttpClientErrorException.NotFound e) {
                System.out.println("Movie with ID " + movieId + " not found.");
            } catch (Exception e) {
                System.out.println("An error occurred while retrieving data from TMDB API. ID: " + movieId);
                e.printStackTrace();
            }
        }
        return movieResponses;
    }
}
