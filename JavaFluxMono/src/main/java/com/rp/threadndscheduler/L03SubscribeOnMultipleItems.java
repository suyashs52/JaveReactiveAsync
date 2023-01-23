package com.rp.threadndscheduler;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L03SubscribeOnMultipleItems {

    public static void main(String[] args) {

        //20 next still executed by 1 thread, all operation executed in sequenctial, process 1 by 1 via 1 thread
        //parallel() is a thread pool for cupu tasks , nt means parallel execution
        //so thread pool shared between multiple publisher and subscriber, but each channel is sequential
        //if 2 subscribeon subscribe on parallel and boundedelastic,both ll be executed by 2thread
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    for (int i = 0; i < 20; i++) {
                        fluxSink.next(i);
                        Util.sleepSeconds(1);
                    }
                    fluxSink.complete();

                })

                .doOnNext(i -> printThreadName("next " + i));

//        Flux<Object> flux1 = flux
//                  .subscribeOn(Schedulers.boundedElastic()) //check bounded change to executer thread
//
        Runnable runnable = () -> flux
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(v -> printThreadName("subs " + v));
        // flux1.subscribe(v -> printThreadName("2sub " + v));
        //flux1

        for (int i = 0; i < 5; i++) {
            new Thread(runnable).start();
        }
        Util.sleepSeconds(50);
    }


    private static void printThreadName(String msg) {
        System.out.println(msg + " \t\t: Thread: " + Thread.currentThread().getName());
    }
}
