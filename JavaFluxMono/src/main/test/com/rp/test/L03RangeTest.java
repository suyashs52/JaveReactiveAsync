package com.rp.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class L03RangeTest {


    @Test
    public void test1(){
        Flux<Integer> range=Flux.range(1,40);

        StepVerifier.create(range)
                .expectNextCount(40)
                .verifyComplete();
    }

    @Test
    public void test2(){
        Flux<Integer> range=Flux.range(1,400);

        StepVerifier.create(range)
                .thenConsumeWhile(i->i<500)//don't know inside of flux, but know that this predicate ll be true
                .verifyComplete();
    }
}
