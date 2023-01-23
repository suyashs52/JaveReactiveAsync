package com.rp.strategy;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

import java.util.ArrayList;
import java.util.List;

public class L05BufferSize {
    public static void main(String[] args) {
        Queues obj;
        List<Object> list=new ArrayList<>();
        System.setProperty("reactor.bufferSize.small","16");
        Flux.create(fluxSink -> {
                    for (int i = 0; i < 201 && !fluxSink.isCancelled(); i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed: " + i);
                        Util.sleepMillis(1);
                    }
                    fluxSink.complete();
                }//, FluxSink.OverflowStrategy.DROP depend on strategy we follow
                )
                .onBackpressureBuffer(20,o-> System.out.println("dropped "+o))//increase the size by given argument

                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i->{
                    Util.sleepMillis(10);
                })
                .subscribe(Util.subscriber())
        ;

        System.out.println(list);

        Util.sleepSeconds(30);
    }
}
