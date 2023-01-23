package com.rp.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L08FluxInterval {


    public static void main(String[] args) {
        //publish item periodically, send update periodically
        Flux.interval(Duration.ofSeconds(1))
                .subscribe(Util.onNext());

        Util.sleepSeconds(5);
    }
}
