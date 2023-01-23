package com.rp.flux.create;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L03FluxTake {

    public static void main(String[] args) {
        //map

        //filter take in between 2 onSubscribe(),request(), onNext()
        //3rd item goes in pipeline take immedietly on 4th cancel the subscritpion after taking 3rd
        //and call onComplete method
        Flux.range(1,10)
                .log()
                .take(3)
                .log()
                .subscribe(Util.subscriber());
    }
}
