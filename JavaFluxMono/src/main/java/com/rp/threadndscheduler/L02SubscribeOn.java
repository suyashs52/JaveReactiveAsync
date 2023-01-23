package com.rp.threadndscheduler;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L02SubscribeOn {

    public static void main(String[] args) {

        //subscribe on> bottom to top to bottom
        //if multiple subscribeon bottomtotop 2nd one take over
        //developer take responsiblity of subscribeOn
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("creae");
                    fluxSink.next(1);
                    fluxSink.next(2);
                })
                .subscribeOn(Schedulers.newParallel("2ndMulitiple"))
                .doOnNext(i -> printThreadName("next " + i));
        Runnable runnab = () -> flux
                .doFirst(() -> printThreadName("first2")) //print by executer thread
                .subscribeOn(Schedulers.boundedElastic()) //check bounded change to executer thread
                .doFirst(() -> printThreadName("first1")) //main thread executig it
                .subscribe(v -> printThreadName("subscribe " + v));


        for (int  i=0;i<2;i++){
            new Thread(runnab).start();
        }
        Util.sleepSeconds(5);
    }


    private static void printThreadName(String msg) {
        System.out.println(msg + " \t\t: Thread: " + Thread.currentThread().getName());
    }
}
