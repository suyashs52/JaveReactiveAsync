package com.rp.combinepublishers;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L04Zip {

    public static void main(String[] args) {
        //whole consider as complete signal..min ll be taken
        Flux.zip(getBody(),getTiers(),getEngine()) //max can we go for 8
                .doOnNext(t-> System.out.println("p:"+t.getT3()))
                .subscribe(Util.subscriber()); //only seeing min item 2 items
    }

    private static Flux<String> getBody(){
        return Flux.range(1,5)
                .map(i->"body");
    }
    private static Flux<String> getEngine(){
        return Flux.range(1,2)
                .map(i->"engine");
    } private static Flux<String> getTiers(){
        return Flux.range(1,6)
                .map(i->"tiers");
    }
}
