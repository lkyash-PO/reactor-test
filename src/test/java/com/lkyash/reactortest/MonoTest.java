package com.lkyash.reactortest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    void firstMono() {
        Mono.just("A")
            .log()
            .subscribe();
    }

    @Test
    void monoWithConsumer() {
        Mono.just("A")
            .log()
            .subscribe(System.out::println);
    }

    @Test
    void monoWithDoOn() {
        Mono.just("A")
            .log()
            .doOnSubscribe(subs -> System.out.println("Subscribed : " + subs))
            .doOnRequest(request -> System.out.println("Requested : " + request))
            .doOnNext(next -> System.out.println("Next : " + next))
            .doOnSuccess(complete -> System.out.println("Success : " + complete))
            .subscribe(System.out::println);
    }

    @Test
    void emptyMono() {
        Mono.empty()
            .log()
            .subscribe(System.out::println);
    }

    @Test
    void emptyCompleteConsumerMono() {
        Mono.empty()
            .log()
            .subscribe(System.out::println,
                       null,
                       () -> System.out.println("Done")
            );
    }

    @Test
    void errorExceptionMono() {
        Mono.error(new Exception())
            .log()
            .subscribe(System.out::println,
                       e -> System.out.println("Exception is: " + e));
    }

    @Test
    void errorDoNoMono() {
        Mono.error(new RuntimeException())
            .doOnError(e -> System.out.println("Exception is: " + e))
            .log()
            .subscribe();
    }

    @Test
    void errorOnErrorResumeMono() {
        Mono.error(new Exception())
            .onErrorResume(e -> {
                System.out.println("Error Is: " + e);
                return Mono.just("B");
            })
            .log()
            .subscribe(System.out::println);
    }
}
