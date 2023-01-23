package com.rp.threadndscheduler;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L06PubSubOn {

    public static void main(String[] args) {
        //boundedElastic is publishOn so nextp and subs executed by boundedElastic
        //create by next parallel
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    for (int i = 0; i < 20; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();

                })

                .doOnNext(i -> printThreadName("next " + i));

        flux.publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("nextp" + i))
                .subscribeOn(Schedulers.parallel())
                .subscribe(v -> printThreadName("subs " + v));

        Util.sleepSeconds(10);

    }


    private static void printThreadName(String msg) {
        System.out.println(msg + " \t\t: Thread: " + Thread.currentThread().getName());
    }
}
