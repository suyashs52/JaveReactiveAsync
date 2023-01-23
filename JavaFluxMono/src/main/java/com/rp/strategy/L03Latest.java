package com.rp.strategy;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

import java.util.ArrayList;
import java.util.List;

public class L03Latest {
    public static void main(String[] args) {
        Queues obj;
        List<Object> list=new ArrayList<>();
        System.setProperty("reactor.bufferSize.small","16");
        Flux.create(fluxSink -> {
                    for (int i = 0; i < 201; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed: " + i);
                        Util.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
                .onBackpressureLatest()
                //after 15 you ll get 110 till 110 dropped
                //latest value read lastly you ll get 200 but in drop you ll get 126 or etc
                //1 new value ll be kept in queue,1 old droped
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
