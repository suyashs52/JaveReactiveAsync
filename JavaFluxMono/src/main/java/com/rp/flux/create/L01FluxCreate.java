package com.rp.flux.create;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L01FluxCreate {

    public static void main(String[] args) {
        Flux<Object> objectFlux = Flux.create(fluxSink -> { //argument is consumer
            //consumer of FluxSink
            fluxSink.next(1);
            fluxSink.next(30);
            fluxSink.complete();
            fluxSink.next(24); //ll not print
        });//consumer

        objectFlux.subscribe(Util.subscriber());
        System.out.println("----country flux sink---");
        objectFlux = Flux.create(fluxSink -> {

            String country;

            do{
                country=Util.faker().country().name();
                fluxSink.next(country);
            }while (!country.toLowerCase().equals("canada"));
            fluxSink.complete();

        });//consumer

        objectFlux.subscribe(Util.subscriber());
    }
}
