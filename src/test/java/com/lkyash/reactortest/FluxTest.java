package com.lkyash.reactortest;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

public class FluxTest {

    @Test
    void firstFlux() {
        Flux.just("A", "B", "C")
            .log()
            .subscribe();
    }

    @Test
    void fluxFromIterable() {
        Flux.fromIterable(Arrays.asList("A", "B", "C"))
            .log()
            .subscribe();
    }

    @Test
    void fluxFromRange() {
        Flux.range(10, 5)
            .log()
            .subscribe();
    }

    @Test
    void fluxFromInterval() throws Exception {
        Flux.interval(Duration.ofSeconds(1))
            .log()
            .take(2)
            .subscribe();
        Thread.sleep(5000);
    }

    @Test
    void fluxRequest() {
        Flux.range(1, 10)
            .log()
            .subscribe(null,
                       null,
                       null,
                       s -> s.request(3)
            );
    }

    @Test
    void fluxCustomSubscriber() {
        Flux.range(1, 10)
            .log()
            .subscribe(new BaseSubscriber<Integer>() {
                int elementsToProcess = 3;

                int count = 0;

                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    System.out.println("Subscribed !!!!");
                    request(elementsToProcess);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    count++;
                    if (count == elementsToProcess) {
                        count = 0;
                        Random r = new Random();
                        elementsToProcess = r.ints(1, 4)
                                             .findFirst().getAsInt();
                        request(elementsToProcess);

                    }
                }
            });
    }

}
