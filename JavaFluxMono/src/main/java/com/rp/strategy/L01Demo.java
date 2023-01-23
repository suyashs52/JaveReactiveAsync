package com.rp.strategy;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L01Demo {
    public static void main(String[] args) {
        //producer produces so many items before subscriber taking first item
        //not good, out of memory issue
        Flux.create(fluxSink -> {
                    for (int i = 0; i < 501; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed: " + i);
                    }
                    fluxSink.complete();
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i->{ //do other processing timeconsuming
                    Util.sleepMillis(10);
                })
                .subscribe(Util.subscriber())
        ;



        Util.sleepSeconds(30);
    }

}
