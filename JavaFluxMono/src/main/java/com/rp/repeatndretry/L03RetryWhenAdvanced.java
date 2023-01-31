package com.rp.repeatndretry;

import com.rp.util.Util;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class L03RetryWhenAdvanced {
    public static void main(String[] args) {
        orderService(Util.faker().business().creditCardNumber())
                .doOnError(err -> System.out.println(err.getMessage()))
              //  .retry(5)
                .retryWhen(Retry.from( //flux
                        flux -> flux.doOnNext(rs -> {
                                    System.out.println("retry time"+rs.totalRetries());
                                    System.out.println("failure" + rs.failure());
                                })
                                .handle((rs, sync) -> {
                                    if (rs.failure().getMessage().equals("500")) { //500 retry for 400 dont retry
                                        sync.next(1);
                                    } else {
                                        sync.error(rs.failure());
                                    }
                                }).delayElements(Duration.ofSeconds(1))
                ))
                .subscribe(Util.subscriber());


        Util.sleepSeconds(40);
    }


    private static Mono<String> orderService(String ccNumber) {
        return Mono.fromSupplier(() -> {
            processPayment(ccNumber);
            return Util.faker().idNumber().valid();
        });
    }

    //payment service
    private static void processPayment(String ccNumber) {
        int random = Util.faker().random().nextInt(1, 10);

        if (random < 8) {
            throw new RuntimeException("500");
        } else if (random < 10) {
            throw new RuntimeException("404");
        }
    }
}
