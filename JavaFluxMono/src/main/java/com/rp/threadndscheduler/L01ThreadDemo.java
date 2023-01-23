package com.rp.threadndscheduler;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L01ThreadDemo {

    public static void main(String[] args) {
        Flux<Object> creae = Flux.create(fluxSink -> {
                    printThreadName("creae");
                    fluxSink.next(1);
                })
                .doOnNext(i -> printThreadName("next " + i));
        creae.subscribe(v -> printThreadName("subscribe " + v)); //main thread executing it

        Runnable runnable=()->creae.subscribe(v->printThreadName("sub "+v));

        for (int i=0;i<2;i++){
            new Thread(runnable).start(); //2 threads are doing it by a thread
        }

        Util.sleepSeconds(5);
    }

    private static void printThreadName(String msg) {
        System.out.println(msg + " \t\t: Thread: " + Thread.currentThread().getName());
    }
}
