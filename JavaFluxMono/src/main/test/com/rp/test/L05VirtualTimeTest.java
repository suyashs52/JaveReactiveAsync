package com.rp.test;

import com.rp.batching.helper.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class L05VirtualTimeTest {


    @Test
    public void tes2() { //if method taking time

//        StepVerifier.create(timeConsumingFlux())
//                .expectNext("1a","2a","3a","4a")
//
//                .verifyComplete(  );
//
        StepVerifier.withVirtualTime(()->timeConsumingFlux())//simulate that time has passed
                .thenAwait(Duration.ofSeconds(30))
                .expectNext("1a","2a","3a","4a")

                .verifyComplete(  );
    }
    @Test
    public void tes3() {
        //first 4 sec nothing should be happening
        StepVerifier.withVirtualTime(()->timeConsumingFlux())//simulate that time has passed
                .expectSubscription() //subscrption exptected
                .expectNoEvent(Duration.ofSeconds(5)) //starting time no event happening, for 5 sec in this case
                .thenAwait(Duration.ofSeconds(20))
                .expectNext("1a","2a","3a","4a")

                .verifyComplete(  );
    }

    private Flux<String> timeConsumingFlux(){
        return Flux.range(1,4)
                .delayElements(Duration.ofSeconds(5))
                .map(i->i+"a");
    }
}
