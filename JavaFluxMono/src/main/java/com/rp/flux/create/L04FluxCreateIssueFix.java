package com.rp.flux.create;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L04FluxCreateIssueFix {

    public static void main(String[] args) {
        Flux<Object> objectFlux = Flux.create(fluxSink -> {
//one instance of fluxsink
            String country;

            do {
                country = Util.faker().country().name();
                System.out.println("emitting: "+country);
                fluxSink.next(country);
            } while (
                    !country.toLowerCase().equals("canada")
                    && fluxSink.isCancelled()==false //if this not written keep emitting the data, take ll
                    //cancel this after n param time
            );
            fluxSink.complete();

        });//consumer
            //source keep sending data , subscriber cancel the data after 3
        objectFlux.take(3).subscribe(Util.subscriber());
        objectFlux.take(3).subscribe(Util.subscriber());
    }
}
