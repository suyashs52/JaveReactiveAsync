package com.rp.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

import java.time.Duration;

public class L06ScenarioNameTest {


    @Test
    public void tes3()  {
        Flux<String> flux=Flux.just("a","b","c");
    //give name to test condition
        StepVerifierOptions scenarioName=StepVerifierOptions.create().scenarioName("alphabet-test");

        StepVerifier.create(flux,scenarioName)
                .expectNextCount(3)
                .verifyComplete();
    } @Test
    public void tes4()  {
        Flux<String> flux=Flux.just("a","b","c");

        StepVerifier.create(flux)
                .expectNext("a")
                .as("a-test") //marks that this failed if failed
                .expectNext("b")
                .as("b-test")
                .expectNext("c")
                .verifyComplete();
    }
}
