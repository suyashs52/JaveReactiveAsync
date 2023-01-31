package com.rp.batching;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L02OverlapndDrop {


    public static void main(String[] args) {
    //same product click 3 times
        eventStream()
              //  .buffer(3,2)//in 3 buffer ,skip 2 old item and add new 2 in next size
                .buffer(3,5)//in 3 buffer ,skip 5 new 2 also skipeed, droppping buffer
                 .subscribe(Util.subscriber());

        Util.sleepSeconds(20);

    }

    private static Flux<String> eventStream(){
        return Flux.interval(Duration.ofMillis(300))
                .map(i->"map"+i);
    }
}
