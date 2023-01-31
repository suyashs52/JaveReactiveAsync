package com.rp.batching;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L05Group {

    public static void main(String[] args) {
        //create a flux bucket and store in it, here we have 2 bucket by even odd key
        //we are invoking process only 2 times
        Flux.range(1,20)
                .delayElements(Duration.ofSeconds(1))
                .groupBy(b->b%2) //key 0,1
                .subscribe(gf->process(gf,(int)gf.key()));

        Util.sleepSeconds(20);
    }

    private static void process(Flux<Integer> flux,int key){
        System.out.println("calling flux");
        flux.subscribe(i-> System.out.println("Key: "+key+", Item: "+i));
    }
}
