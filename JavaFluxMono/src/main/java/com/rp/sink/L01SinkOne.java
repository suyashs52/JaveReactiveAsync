package com.rp.sink;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class L01SinkOne {

    //work as publisher and subcriber
    //sink are thread safe
    public static void main(String[] args) {
        Flux.create(fluxSink -> {})
                ;
        //capable of emiting one vlaue
        Sinks.One<Object> sink=Sinks.one();
        Mono<Object> objectMono = sink.asMono();//get value

        objectMono.subscribe(Util.subscriber("sam"));

        sink.tryEmitValue("hi"); //emit value called this too
    }
}
