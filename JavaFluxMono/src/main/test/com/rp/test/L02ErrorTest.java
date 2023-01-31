package com.rp.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class L02ErrorTest {

    @Test
    public void test1(){
        Flux<Integer> just=Flux.just(1,2,3);
        Flux<Integer> error=Flux.error(new RuntimeException("error"));
        Flux<Integer> concat = Flux.concat(just, error);
        StepVerifier.create(concat)
                .expectNext(1,2,3)
               .verifyError();
               // .verifyError(IllegalStateException.class);


    }
    @Test
    public void test2(){
        Flux<Integer> just=Flux.just(1,2,3);
        Flux<Integer> error=Flux.error(new RuntimeException("error"));
        Flux<Integer> concat = Flux.concat(just, error);
        StepVerifier.create(concat)
                .expectNext(1,2,3)
               // .verifyError();
                .verifyError(RuntimeException.class);


    }

    @Test
    public void test3(){
        Flux<Integer> just=Flux.just(1,2,3);
        Flux<Integer> error=Flux.error(new RuntimeException("error"));
        Flux<Integer> concat = Flux.concat(just, error);
        StepVerifier.create(concat)
                .expectNext(1,2,3)
               // .verifyError();
                .verifyErrorMessage("error");


    }
}
