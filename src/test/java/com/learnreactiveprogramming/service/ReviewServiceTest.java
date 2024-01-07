package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    private WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();
    private ReviewService reviewService = new ReviewService(webClient);

    @Test
    void retrieveReviewsFlux_RestClient() {
        // given
        Long movieInfoId = 1L;

        // when
        var reviewsFlux = reviewService.retrieveReviewsFlux_RestClient(movieInfoId);

        // then
        StepVerifier.create(reviewsFlux)
                .assertNext(review -> {
                    assertEquals(movieInfoId, review.getMovieInfoId());
                    assertEquals( "Nolan is the real superhero", review.getComment());
                })
                .verifyComplete();
    }
}