package com.lkyash.reactortest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MapOperator {

    @Test
    void map() {
        Flux.range(1, 10)
            .log()
            .map(i -> i * 10)
            .subscribe(System.out::println);
    }

    @Test
    void flatMap() {
        Flux.range(1, 5)
            .log()
            .flatMap(i -> Flux.range(i * 10, 2))
            .subscribe(System.out::println);
    }

    @Test
    void flatMapMany() {
        Mono.just(5)
            .log()
            .flatMapMany(i -> Flux.range(1, i))
            .log()
            .flatMap(i -> Flux.range(i * 10, 2))
            .subscribe(System.out::println);

    }

    @Test
    void concat() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                                      .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                                     .delayElements(Duration.ofMillis(400));
        Flux.concat(oneToFive, sixToTen)
            .subscribe(System.out::println);
        Thread.sleep(4000);
    }

    @Test
    void merge() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                                      .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                                     .delayElements(Duration.ofMillis(400));
        Flux.merge(oneToFive, sixToTen)
            .subscribe(System.out::println);
        Thread.sleep(4000);
    }

    @Test
    void zip() {
        Flux<Integer> oneToFive = Flux.range(1, 5);
        Flux<Integer> sixToTen = Flux.range(6, 5);
        Flux.zip(oneToFive, sixToTen, (one, two) ->
                one + " , " + two
        ).subscribe(System.out::println);
    }
}
