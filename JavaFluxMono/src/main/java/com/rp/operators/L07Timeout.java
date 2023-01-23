package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L07Timeout {

    public static void main(String[] args) {
        getOrderNumbers()
                .timeout(Duration.ofSeconds(2),fallback()) //flux taking 5 sec, timout is 2...ll get error,
                //if fallback define , fallback producer ll be called
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);
    }

    private static Flux<Integer> getOrderNumbers(){
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(5));
    }


    private static Flux<Integer> fallback(){
        return Flux.range(100,10)
                .delayElements(Duration.ofMillis(200));
    }
}
