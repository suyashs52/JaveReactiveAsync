package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L09SwitchIfEmpty {


    public static void main(String[] args) {
        getOrderNumbers()
                .filter(i -> i > 11) //if no record found swith to different flux
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }

    //redis data if not present
    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(2, 10);
    }
    //call the db
    private static Flux<Integer> fallback() {
        return Flux.range(2, 20);
    }
}

