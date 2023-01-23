package com.rp.mono;

import com.rp.util.Util;
import reactor.core.publisher.Mono;

public class L03MonoSubscribe {

    public static void main(String[] args) {
        //publisher
        Mono<Integer> mono=    Mono.just("ball")
                .map(s->s.length())//String::length
                .map(l->l/1); //change to 0 or 1

        //1
       // System.out.println( mono.subscribe()); //when you run this nothing ll be provided
        //if you ll do this when there is exception we dont specify what we ll do exception thrown
        //if yes like below: then it ll like do the logic
        mono.subscribe(     //on next called then completed
                item-> System.out.println(item),
                err-> System.out.println(err.getMessage()),
                ()-> System.out.println("Completed-->")
        ) ;

        System.out.println("3rd way");
        //3
        mono.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );
    }
}
