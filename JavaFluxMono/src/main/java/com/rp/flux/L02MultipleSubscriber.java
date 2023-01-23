package com.rp.flux;

import reactor.core.publisher.Flux;

public class L02MultipleSubscriber {
    public static void main(String[] args) {
        Flux<Integer> f=Flux.just(1,2,3,4,5);
        f.filter(i->i%2==0).subscribe(i-> System.out.println("Sub1: "+i));
        f.subscribe(i-> System.out.println("Sub2: "+i));
    }

}
