package com.rp.assignment.hotndcold;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

public class OrderService {
    //pushing the upddate

    private Flux<PurchaseOrder> flux;

    public Flux<PurchaseOrder> orderStream(){
        if(Objects.isNull(flux)){
            flux=getOrderStream();
        }
        return flux;
    }

    private Flux<PurchaseOrder> getOrderStream(){
        return Flux.interval(Duration.ofMillis(100))
                .map(i->new PurchaseOrder())
                .publish()
                .refCount(2);
    }
}
