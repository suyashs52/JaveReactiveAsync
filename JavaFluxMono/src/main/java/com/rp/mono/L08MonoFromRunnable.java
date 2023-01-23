package com.rp.mono;

import com.rp.util.Util;
import reactor.core.publisher.Mono;

public class L08MonoFromRunnable {

    public static void main(String[] args) {
        Runnable runnable=()-> System.out.println("ttt");
        //use when some process complete and you want to notifiy after completiong

        Mono.fromRunnable(runnable).subscribe(Util.onNext());
        Mono.fromRunnable(timeConsumingProcess()).subscribe(Util.onNext(),Util.onError(),()->{
            System.out.println("process completed..sending email....");
        });

        get().subscribe(Util.onNext(),Util.onError(),Util.onComplete()); //oncomplete called only
        System.out.println("main thread ended");

    }

    private static  Runnable timeConsumingProcess(){
        return ()->{
            System.out.println("Operation started");
            Util.sleepSeconds(3);
            System.out.println("operation completed");
        };
    }

    private static Mono<Integer> get(){
        return Mono.fromRunnable(()->{ //empty return only
            int a=1+2;
            //return a;
        });
    }
}
