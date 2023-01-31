package com.rp.batching;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L01Buffer {

    //click event on website store it somewhere and send it to flux
    //buffer store in a list
    public static void main(String[] args) {

        eventStream()
                .buffer(5) //store max 5 itmes and sent all together
                .buffer(Duration.ofSeconds(2))//2 secs how many items come store and then send
                .bufferTimeout(5,Duration.ofSeconds(2))//hybrid of if events are much high,min condition taken
                .subscribe(Util.subscriber());

        Util.sleepSeconds(40);

    }

    private static Flux<String> eventStream(){
        return Flux.interval(Duration.ofMillis(300))
                .map(i->"map"+i);
    }
}
