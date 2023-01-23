package com.rp.flux.create;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class L06FluxGenerateUntill {

    public static void main(String[] args) {

        //max is 10
        //subscriber is cancels
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Flux.generate(synchronousSink -> {
                    String coutnry = Util.faker().country().name();
                    System.out.println(atomicInteger+ " emitting " + coutnry);
                    atomicInteger.getAndAccumulate(1, Integer::sum);
                    synchronousSink.next(coutnry);

                    if (coutnry.equalsIgnoreCase("canada") || atomicInteger.get() == 10) {
                        synchronousSink.complete();
                    }
                })
                .subscribe(Util.subscriber());

        atomicInteger.set(-100);
    }
}
