package com.rp.threadndscheduler;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.net.URL;
import java.time.Duration;

public class L08FluxInterval {

    public static void main(String[] args) {
        Flux.interval(Duration.ofSeconds(1)) //interval is async because it uses Scheduler.parallel() internally
                //so cpu intensive task,check behaviour by halting main thread
                //if you change nothing work,again it is parallel
                .subscribeOn(Schedulers.boundedElastic())

                .subscribe(Util.subscriber());

        Util.sleepSeconds(14);
    }
}
