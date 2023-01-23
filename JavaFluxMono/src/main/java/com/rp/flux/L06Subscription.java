package com.rp.flux;

import com.rp.util.Util;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

public class L06Subscription {

    public static void main(String[] args) {

        AtomicReference<Subscription> atomicReference=new AtomicReference<>();

        Flux.range(1,20)
                .log()
                .subscribeWith(new Subscriber<Integer>() { //nothing happend when you do subscribeWith
                    //because subscriber didn't request for any item from publisher
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("Received Sub: "+subscription);
                        atomicReference.set(subscription);
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: "+integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("onError: "+throwable.getMessage());

                    }

                    @Override
                    public void onComplete() {

                        System.out.println("onComplete");

                    }
                }) ;  //custome implementation of subscriber


        Util.sleepSeconds(3);

        atomicReference.get().request(3); //request for 3 itemrs

        Util.sleepSeconds(4);
        atomicReference.get().request(2);
        Util.sleepSeconds(3);
        System.out.println("cancelling...");
        atomicReference.get().cancel(); //didn't get any item after cancelling
        Util.sleepSeconds(3); //holding the request
        atomicReference.get().request(3);
    }
}
