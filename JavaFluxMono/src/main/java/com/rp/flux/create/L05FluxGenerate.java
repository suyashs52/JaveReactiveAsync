package com.rp.flux.create;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L05FluxGenerate {

    public static void main(String[] args) {
    //allow to emit only one data, create you can emit any number of data
        //not like mono
        //no uncommenct one--infinite loop
        //generate ll take care of loop,invoking again and again
        //so synchronousSink each object generate each time
        Flux<Object> generate = Flux.generate(synchronousSink -> {
            System.out.println("emitting data...");
            synchronousSink.next(Util.faker().country().name());

           // synchronousSink.next(Util.faker().name());
            synchronousSink.complete();//this will complete no take wait for next
        });
        //generate will take care of fluxSink isCancelled
        generate.take(2).subscribe(Util.subscriber());
    }
}
