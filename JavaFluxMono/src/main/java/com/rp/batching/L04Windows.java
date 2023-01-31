package com.rp.batching;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class L04Windows {

    private static AtomicInteger atomicInteger=new AtomicInteger(10);
    public static void main(String[] args) {
        //window release flux, so can get item instantly not like buffer wait for list to fill then release

        eventStream()
                //.window(5)
                .window(Duration.ofSeconds(2))//open 2 sec windows
                .flatMap(flux->saveEvents(flux))//to get the result use flatmap, map return flux need to subscribe
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);
    }

    private static Flux<String> eventStream(){
        return Flux.interval(Duration.ofMillis(300))
                .map(i->"event"+i);
    }

    private static Mono<Integer> saveEvents(Flux<String> flux){
        return flux
                .doOnNext(e-> System.out.println("Saving "+e))
                .doOnComplete(()->{
                    System.out.println("saved the batch");
                    System.out.println("-----------");
                }) //just flux complete then return the mono
                .then(Mono.just(atomicInteger.getAndIncrement()));

    }
}
