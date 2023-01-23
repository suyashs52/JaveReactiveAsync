package com.rp.combinepublishers;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L02Concat {

    public static void main(String[] args) {
        //concat has lazy behavioru
        Flux<String> flux1 = Flux.just("a", "b");
        Flux<String> flux2=Flux.just("c","d","e");
        Flux<String> fluxe=Flux.error(new RuntimeException("error"));
//add flux sequenctially
     //   Flux<String> flux=flux1.concatWith(flux2);
        Flux<String> flux=Flux.concat(flux1,flux2,flux1,fluxe);

        flux.subscribe(Util.subscriber());
    }
}
