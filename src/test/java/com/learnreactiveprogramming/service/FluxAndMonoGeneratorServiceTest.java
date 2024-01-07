package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    private FluxAndMonoGeneratorService generatorService = new FluxAndMonoGeneratorService();

    @Test
    void namesMono() {
        // when
        var namesMono = generatorService.namesMono();

        // then
        StepVerifier.create(namesMono).expectNext("alex").verifyComplete();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 5})
    void namesMono_map_filter(int length) {
        // when
        var namesMono = generatorService.namesMono_map_filter(length);

        // then
        if (length == 3) {
            StepVerifier.create(namesMono).expectNext("ALEX").verifyComplete();
        } else if (length == 5) {
            StepVerifier.create(namesMono).expectNext("DEFAULT").verifyComplete();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 5})
    void namesMono_map_filter_switchIfEmpty(int length) {
        // when
        var namesMono = generatorService.namesMono_map_filter_switchIfEmpty(length);

        // then
        if (length == 3) {
            StepVerifier.create(namesMono).expectNext("ALEX").verifyComplete();
        } else if (length == 5) {
            StepVerifier.create(namesMono).expectNext("DEFAULT").verifyComplete();
        }
    }

    @Test
    void explore_concatWith_mono() {

        // when
        var names = generatorService.explore_concatWith_mono();

        // then
        StepVerifier.create(names)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void explore_concatWith() {

        // when
        var names = generatorService.explore_concatWith();

        // then
        StepVerifier.create(names)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_mergeWith_mono() {

        // when
        var names = generatorService.explore_mergeWith_mono();

        // then
        StepVerifier.create(names)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void explore_mergeWith() {

        // when
        var names = generatorService.explore_mergeWith();

        // then
        StepVerifier.create(names)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void explore_zipWith_mono() {

        // when
        var names = generatorService.explore_zipWith_mono().log();

        // then
        StepVerifier.create(names)
                .expectNext("AB")
                .verifyComplete();
    }

    @Test
    void explore_zipWith() {

        // when
        var names = generatorService.explore_zipWith().log();

        // then
        StepVerifier.create(names)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void exception_mono_onErrorContinue() {

        {
            // Given
            String input = "reactor";

            // when
            var mono = generatorService.exception_mono_onErrorContinue(input);

            // then
            StepVerifier.create(mono)
                    .expectNext(input)
                    .verifyComplete();
        }

        {
            // Given
            String input = "abc";

            // when
            var mono = generatorService.exception_mono_onErrorContinue(input);

            // then
            StepVerifier.create(mono)
                    .verifyComplete();
        }
    }
}