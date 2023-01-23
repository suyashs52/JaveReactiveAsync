package com.rp.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class L09FluxFromMono {
    public static void main(String[] args) {
        Mono<String> mono=Mono.just("a");

        Flux<String> from = Flux.from(mono);

        from.subscribe(Util.onNext());

        Flux.range(1,10)
                .filter(i->i>3)
                .next() //make this as mono the first item
                .subscribe(Util.onNext(),Util.onError(),Util.onComplete());
    }

    private static void doSomething(Flux<String> flux){

    }
}
