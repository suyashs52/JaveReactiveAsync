package com.rp.mono;

import com.rp.util.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class L06MonoFromSupplier {

    public static void main(String[] args) {

        //use just only hwne you have data already> see print ll use
       // Mono<String> mono = Mono.just(getName());
        //supplier ll give you one object,if somebody invoke this , we ll be supplying then only
        Mono<String> supplier = Mono.fromSupplier(() -> getName());

        //this ll print;
        supplier.subscribe();
        supplier.subscribe(Util.onNext());

        Supplier<String> stringSupplier=()->getName();
        Callable<String> stringCallable=()->getName();//this is concurrent
        System.out.println("Callable demol..");
        Mono<String> stringMono = Mono.fromCallable(stringCallable);//won't invoke the method
        stringMono.subscribe(Util.onNext());
    }
    private  static String getName(){
        System.out.println("Generating name....");

        return Util.faker().name().fullName();
    }
}
