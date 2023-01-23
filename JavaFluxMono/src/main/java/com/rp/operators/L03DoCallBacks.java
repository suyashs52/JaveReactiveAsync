package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L03DoCallBacks {

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
                    System.out.println("inside create");
                    for (int i = 0; i < 10; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                    System.out.println("--completed--");
                }).doOnComplete(() -> {
                    System.out.println("doOnComplete");
                })

                .doFirst(() -> System.out.println("doFirst"))
                .doOnNext(o -> System.out.println("doOnNext: " + o))
                .doOnSubscribe(s -> System.out.println("doOnSubscribe " + s)) //give subscrition request cancel
                .doOnRequest(l -> System.out.println("doOnRequest " + l)) //how many item are requesting
                .doOnTerminate(() -> System.out.println("Terminsated"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doFinally(signalType -> System.out.println("doFinally: " + signalType))
                .doOnDiscard(Object.class, o -> System.out.println("donOnDiscard" + o))
                .subscribe(Util.subscriber());
    }
}
