package com.rp.mono;

import reactor.core.publisher.Mono;

public class L01MonoJust {

    public static void main(String[] args) {
        //publisher
        Mono<Integer> mono = Mono.just(1);
        System.out.println(mono);

        mono.subscribe(i-> System.out.println("Received : " +
                ""+i)); //if you give me integer i ll print it
    }
}
