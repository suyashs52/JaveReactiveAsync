package com.rp.strategy;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class L01Drop {
    public static void main(String[] args) {
        Queues obj;
        List<Object> list=new ArrayList<>();
        System.setProperty("reactor.bufferSize.small","16");
          Flux.create(fluxSink -> {
                    for (int i = 0; i < 501; i++) {
                        System.out.println("BPush: " + i);
                        fluxSink.next(i);
                        System.out.println("Pushed: " + i);
                        Util.sleepMillis(1); //pushed takes time so subscribe more value but after 15 you ll see 110 value becuaes
                        //after it read 15 new item were inserted
                        //new items dropped once subscriber read then new item ll be pushed
                        //once you take 75% of item queue will be refilled
                        //save saving the application from out of memory
                    }
                    fluxSink.complete();
                })
                //  .onBackpressureDrop() //receiver receive only 255 items,publisher emitted to 500,queue default size is 256
                  .onBackpressureDrop(list::add)
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
