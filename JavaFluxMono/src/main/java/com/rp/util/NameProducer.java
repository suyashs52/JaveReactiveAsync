package com.rp.util;

import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameProducer implements Consumer<FluxSink<String>> {

    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        System.out.println("stringFluxSink:  "+stringFluxSink);
        this.fluxSink=stringFluxSink;

    }

    public void produce(){
        String name=Util.faker().name().fullName();
        String thread=Thread.currentThread().getName();
        this.fluxSink.next(name+" : "+thread);
    }
}
