package com.rp.assignment.combinepublishers;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Assignment {

    public static void main(String[] args) {
        int intialCarPrince = 10000;
        Flux.combineLatest(monthsStream(), demandStream(), (m, d) -> {
                    return (10000 - (m * 100)) * d;
                })
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }

    private static Flux<Long> monthsStream() {
        return Flux.interval(Duration.ofSeconds(1)); //for a month

    }

    private static Flux<Double> demandStream() {
        //start with 1 in 3 sec give randome value between .80 -.120
        return Flux.interval(Duration.ofSeconds(3))
                .map(i -> Util.faker().random().nextInt(80, 120) / 100d)
                .startWith(1d);
    }
}
