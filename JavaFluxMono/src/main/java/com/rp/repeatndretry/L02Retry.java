package com.rp.repeatndretry;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class L02Retry {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        getIntegers()
                .retry(2) //retry 2 time when there is an error
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(3)))//retry with dealy of 3 sec by 2 times
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);
    }


    private static Flux<Integer> getIntegers() {
        return Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("Completed"))
                .map(i -> atomicInteger.getAndIncrement())
                //   .map(i->i/0)
                .map(i -> i / (Util.faker().random().nextInt(1, 5) > 3 ? 0 : 1))
                .doOnError(err -> System.out.println("--error---"))
                ;
    }
}
