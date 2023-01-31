package com.rp.repeatndretry;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class L01Repeat {

    private static AtomicInteger atomicInteger=new AtomicInteger(1);
    public static void main(String[] args) {
        //repeat: resubscribe after complete signal
        //retry: resubscribe after error signal

        getIntegers()
               // .repeat(2) //after receiveing complete repeat 2 times
                .repeat(()->atomicInteger.get()<14) //after receiveing check condition
                .subscribe(Util.subscriber());


    }

    private static Flux<Integer> getIntegers(){
        return Flux.range(1,3)
                .doOnSubscribe(s-> System.out.println("Subscribed"))
                .doOnComplete(()-> System.out.println("Completed"))
                .map(i->atomicInteger.getAndIncrement());
    }
}
