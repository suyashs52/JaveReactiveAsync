package com.rp.combinepublishers;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class L01StartWith {

    public static void main(String[] args) {
        //first time generate ,2nd time get from existing if match found
        //--1---
        //---2--
        //subscriber subscribe to 2nd guy first drining completed once completed then comes to first
        //if completed by 2nd dont' touch first
        NameGenerator generator = new NameGenerator();
        generator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("sam"));

        generator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("mike"));

        generator.generateNames()
                .take(3)
                .subscribe(Util.subscriber("jake"));

        generator.generateNames()
                .filter(f->f.contains("a"))
                .take(3)
                .subscribe(Util.subscriber("jake2"));
        System.out.println("taking after long time");
        generator.generateNames()

                .take(4)
                .subscribe(Util.subscriber("jake3"));


        System.out.println("---------------------");
        Flux.just("a","b","c")
                .startWith(Flux.just("a","b","c"))
                .subscribe(Util.subscriber());
    }
}

class NameGenerator {

    private List<String> list = new ArrayList<>();

    public Flux<String> generateNames() {
        return Flux.generate(stringSynchronousSink -> {
                    System.out.println("genereated fresh");
                    Util.sleepSeconds(1);
                    String name = Util.faker().name().firstName();
                    list.add(name);
                    stringSynchronousSink.next(name);
                })
                .cast(String.class)
                .startWith(getFromCache()); //ll start from cache, first it checks here if yes then return otherwise get inside

    }

    private Flux<String> getFromCache() {
        return Flux.fromIterable(list);
    }
}