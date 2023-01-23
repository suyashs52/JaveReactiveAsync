package com.rp.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L01FluxIntro {

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(10, 20,30,40);

        flux.subscribe(Util.onNext());
        Flux.just(Flux.empty()).subscribe(Util.onNext());


    }
}
