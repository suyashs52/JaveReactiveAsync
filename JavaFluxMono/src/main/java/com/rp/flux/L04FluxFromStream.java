package com.rp.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class L04FluxFromStream {

    public static void main(String[] args) {
        List<Integer> list=List.of(1,2,3,4,5);

        Stream<Integer> stream = list.stream();

       // stream.forEach(System.out::println); //terminal operator , can't use the stream again
       // stream.forEach(System.out::println); //this ll throw the exception

        Flux<Integer> integerFlux = Flux.fromStream(stream);
        integerFlux.subscribe(Util.onNext());
       // integerFlux.subscribe(Util.onNext());//this ll get the issue

        System.out.println("did nt give error");
        Flux<Integer> integerFlux1=Flux.fromStream(()->list.stream()); //create a stream part of supplier
        integerFlux1.subscribe(Util.onNext());
        integerFlux1.subscribe(Util.onNext());


    }
}
