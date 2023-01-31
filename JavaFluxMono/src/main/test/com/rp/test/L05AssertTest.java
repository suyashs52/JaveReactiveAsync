package com.rp.test;

import com.rp.batching.helper.BookOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class L05AssertTest {

    @Test
    public void test() {
        Mono<BookOrder> mono = Mono.fromSupplier(() -> new BookOrder());

        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .verifyComplete();
    }

    @Test
    public void tes2() { //if method taking time
        Mono<BookOrder> mono = Mono.fromSupplier(() -> new BookOrder())
                .delayElement(Duration.ofSeconds(3));

        StepVerifier.create(mono)
                .assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
                .expectComplete()
                .verify(Duration.ofSeconds(4)); //should be completed in this duration
    }
}

