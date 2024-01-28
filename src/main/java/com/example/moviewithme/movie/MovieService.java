// MovieService.java

package com.example.moviewithme.movie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class MovieService {

    @Value("${omdbapi.apikey}")
    private String OMDBAPI_API_KEY;

    private static final String OMDBAPI_API_URL = "http://www.omdbapi.com/";

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;

    public MovieService(MovieRepository movieRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
    }

    public List<Movie> getMoviesByTitle(String title) {
        String url = UriComponentsBuilder.fromUriString(OMDBAPI_API_URL)
                .queryParam("apikey", OMDBAPI_API_KEY)
                .queryParam("s", title)
                .build().toUriString();

        MovieApiResponse response = restTemplate.getForObject(url, MovieApiResponse.class);

        if (response != null && response.getResults() != null) {
            for (MovieApiResponse.MovieData movieData : response.getResults()) {
                Movie movie = new Movie(movieData.getOriginalTitle(), Long.parseLong(movieData.getImdbID().substring(2)));
                movieRepository.save(movie);
            }
        }

        return movieRepository.findAll();
    }
}
