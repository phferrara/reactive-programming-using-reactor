package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class MovieInfoServiceTest {

    private WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();
    private MovieInfoService movieInfoService = new MovieInfoService(webClient);

    @Test
    void retrieveMovieInfoById_RestClient() {

        // given
        Long movieInfoId = 1L;

        // when
        var movieInfoFlux = movieInfoService.retrieveMovieInfoById_RestClient(movieInfoId);

        // then
        StepVerifier.create(movieInfoFlux)
                .assertNext(movieInfo -> assertEquals("Batman Begins", movieInfo.getName()))
                .verifyComplete();
    }
}