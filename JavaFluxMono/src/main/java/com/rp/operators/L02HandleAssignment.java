package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L02HandleAssignment {

    public static void main(String[] args) {
        Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.faker().country().name());
        }).map(Object::toString)
                .handle((s,synchronousSink)->{
                    synchronousSink.next(s);
                    if(s.toLowerCase().equals("canada")){
                        synchronousSink.complete();
                    }
                }).subscribe(Util.subscriber());
    }
}
