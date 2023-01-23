package com.rp.assignment.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class StockPricePublisher {
    public static Flux<Integer> getPrice(){
        AtomicInteger atomicInteger=new AtomicInteger(100);
        //on 100 , increase or decrease this by random number
        return Flux.interval(Duration.ofSeconds(1))
                .map(i->atomicInteger.getAndAccumulate(
                        Util.faker().random().nextInt(-5,5)
                        ,Integer::sum//(a,b)->a+b
                ));
    }
}
