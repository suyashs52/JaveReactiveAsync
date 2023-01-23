package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class L06OnError {

    public static void main(String[] args) {

        Flux.range(1,10)
                .log()
                .map(i->10/(5-i))
                //.onErrorReturn(-1) not continue after error
                .onErrorResume(e->fallback()) //not continue after error
                .onErrorContinue((err,obj)->{
                    System.out.println("error-->"+err);
                }) //err and object that cause the issue
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback(){ //publisher
        return Mono.fromSupplier(()->Util.faker().random().nextInt(100,200));
    }
}
