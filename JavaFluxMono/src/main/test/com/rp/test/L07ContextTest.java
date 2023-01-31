package com.rp.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class L07ContextTest {

    @Test
    public void test1()  {

        StepVerifier.create(getWelcomeMessage())
               // .expectNext()
                .expectError(RuntimeException.class);
               // .verifyError(RuntimeException.class);
    }

    @Test
    public void test3()  {

        StepVerifierOptions options = StepVerifierOptions.create().withInitialContext(Context.of("user", "same"));

        StepVerifier.create(getWelcomeMessage(),options)
                .expectNext("welcome same")
                .verifyComplete();
    }


    private static Mono<Object> getWelcomeMessage(){


        return  Mono.deferContextual(ctx->{
            if(  ctx.hasKey("user")){
                return Mono.just("welcome "+ctx.get("user"));
            }else {
                return Mono.just(new RuntimeException("unauthenticalted"));
            }
        });

    }
}
