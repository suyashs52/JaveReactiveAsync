package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L04LimitRate {
    //check document
    public static void main(String[] args) {
        Flux.range(1,100)
                .log()
                .limitRate(100,88)
                .subscribe(Util.subscriber());
    }
}
