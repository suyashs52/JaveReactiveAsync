package com.rp.combinepublishers;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L05CombineLatest {

    public static void main(String[] args) {

        //combine latest of all the publisher
        //a----b-----c----d--
        //--------1----2------
        //--------b1-c1-c2-d2---
        //bifunction ll give latest value
        //log file if any new file comes it goes for new file
        Flux.combineLatest(getString(),getNumbers(),(s,i)->s+i)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(30);
    }

    private static Flux<String> getString(){
        return Flux.just("a","b","c","d")
                .delayElements(Duration.ofSeconds(1));
    }

    private static Flux<Integer> getNumbers(){
        return Flux.just(1,10)
                .delayElements(Duration.ofSeconds(3));
    }


}
