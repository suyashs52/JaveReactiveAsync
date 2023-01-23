package com.rp.mono;

import com.rp.util.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class L06MonoFromSupplierAdvn {
    public static void main(String[] args) {
        //so thread was not block, sout ll be executed
        getName();
        getName();
        getName();
        //we are building the pipeline not executing it, so executing ll take 3 secs
        getName();        ;
       // getName().subscribe(Util.onNext()); //here i am subscribing hey i need data so blocked
        getName().subscribeOn(Schedulers.boundedElastic()).subscribe(Util.onNext()); //blocked()
        System.out.println("bounded elastic"); //async above not blocked the thread
        getName();

        Util.sleepSeconds(4);//wait for other thread to compelte,block the main thread
    }

    public static Mono<String> getName(){
        System.out.println("Entered getname method");
        return Mono.fromSupplier(()->{
            System.out.println("Generating name from supplier...");
            Util.sleepSeconds(3);
            return Util.faker().name().fullName();
        }).map(String::toUpperCase);
    }


}
