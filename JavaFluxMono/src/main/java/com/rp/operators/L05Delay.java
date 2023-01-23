package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L05Delay {

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.x","10");
        Flux.range(1,100)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());
        //internally it ratelimit to 32 , after drainng to <32 (75%) in between it again request for next element
    //in queue > property reactor.buffersize=32 and its multiple
        Util.sleepSeconds(60);
    }
}
