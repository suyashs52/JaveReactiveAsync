package com.rp.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L05FluxRange {
    public static void main(String[] args) {
        Flux.range(10,10).subscribe(Util.onNext()); //new way for for loop

        Flux.range(2,10) //publisher
                .log()
                .map(i->Util.faker().name().fullName()) //subscriber pass subscritpion object
                .log()
                .subscribe(Util.onNext()); //request onbound > just give me data
    }
}
