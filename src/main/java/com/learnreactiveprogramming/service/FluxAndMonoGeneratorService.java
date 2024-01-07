package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.exception.ReactorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;


public class FluxAndMonoGeneratorService {

    public Mono<String> namesMono() {
        return Mono.just("alex");
    }

    public Mono<String> namesMono_map_filter(int stringLength) {
        return Mono.just("alex")
                .filter(item -> item.length() > stringLength)
                .defaultIfEmpty("default")
                .map(String::toUpperCase);
    }

    public Mono<String> namesMono_map_filter_switchIfEmpty_v2(int stringLength) {
        Function<Mono<String>, Mono<String>> filterMap = item -> item.filter(name -> name.length() > stringLength)
                .map(String::toUpperCase);
        var defaultFlux = Mono.just("default").transform(filterMap);
        return Mono.just("alex").transform(filterMap).switchIfEmpty(defaultFlux);
    }

    public Mono<String> namesMono_map_filter_switchIfEmpty(int stringLength) {
        Mono<String> defaultMono = Mono.just("DEFAULT");
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .switchIfEmpty(defaultMono);
    }

    public Flux<String> explore_concatWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Flux.just("B");
        return aMono.concatWith(bMono);
    }

    public Flux<String> explore_concatWith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(defFlux).log();
    }

    // "A", "D", "B", "E", "C", "F"
    // Flux is subscribed early
    public Flux<String> explore_mergeWith() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return abcFlux.mergeWith(defFlux).log();


    }

    public Flux<String> explore_mergeWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Flux.just("B");
        return aMono.mergeWith(bMono);
    }

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"));
    }

    // AD, BE, FC
    // AD, BE, CF
    public Flux<String> explore_zipWith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.zipWith(defFlux, (first, second) -> first + second);


    }

    public Mono<String> explore_zipWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.zipWith(bMono)
                .map(t2 -> t2.getT1() + t2.getT2());
    }

    public Mono<Object> exception_mono_onErrorMap() {
        return Mono.just("B")
                .map(item -> {
                    throw new RuntimeException("Exception occurred");
                })
                .onErrorMap(exception -> new ReactorException(exception, exception.getMessage()));
    }

    public Mono<String> exception_mono_onErrorContinue(String input) {
        return Mono.just(input)
                .map(name -> {
                    if (name.equals("abc")) {
                        throw new RuntimeException("Exception Occurred");
                    }
                    return name;
                })
                .onErrorContinue((exception, value) -> {
                    System.out.println("Value is : " + value);
                    System.out.println("Exception is : " + exception.getMessage());
                });
    }
}
