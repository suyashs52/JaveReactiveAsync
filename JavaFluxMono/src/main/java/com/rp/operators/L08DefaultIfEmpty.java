package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L08DefaultIfEmpty {

    public static void main(String[] args) {
        getOrderNumbers()
                .filter(i->i>100) //get the default flux if nothing match
                .defaultIfEmpty(-100)
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> getOrderNumbers(){
        return Flux.range(2,10);
    }
}
