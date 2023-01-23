package com.rp.threadndscheduler;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L04PublishOn {

    public static void main(String[] args) {
        //publishOn up to down- so downstream, once see different thread ll execute it
        //so subscribe on ll effect the publisher after the next steps , when it sees publishOn it change to as it is
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    for (int i = 0; i < 20; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();

                })

                .doOnNext(i -> printThreadName("next " + i));

        flux.publishOn(Schedulers.boundedElastic())//io intensvie task
                .doOnNext(i -> printThreadName("nextp" + i)) //this ll execute by boundedElastic
                .publishOn(Schedulers.parallel()) //new thread: cpu intensive task
                .subscribe(v -> printThreadName("subs " + v));//this ll too by parallel so 3 threads

        Util.sleepSeconds(10);

    }


    private static void printThreadName(String msg) {
        System.out.println(msg + " \t\t: Thread: " + Thread.currentThread().getName());
    }
}
