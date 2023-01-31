package com.rp.batching;

import com.rp.util.Util;
import com.rp.util.entity.PurchaseOrder;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class L06Assignment{
    //kid has discount of 50 , other has tax of 10 percent
    //kids ll get one free item
    public static void main(String[] args) {
        Map<String,Function<Flux<PurchaseOrder>,Flux<PurchaseOrder>>> map=Map.of(
                "Kids",OrderProcessor.kidsProcessing(),
                "Automotive",OrderProcessor.automotiveProcessing()
        );

        Set<String> set=map.keySet();
        OrderService.orderStream()
                .filter(p->set.contains(p.getCategory()))
                .groupBy(PurchaseOrder::getCategory) //2 keys as 2 category
                .flatMap(gf-> map.get( gf.key()).apply(gf)) //hey map do you have any processing for this key
                .subscribe(Util.subscriber());

        Util.sleepSeconds(20);
    }


}
class OrderService {

    public static Flux<PurchaseOrder> orderStream(){
        return Flux.interval(Duration.ofMillis(100))
                .map(i->new PurchaseOrder());
    }
}
class OrderProcessor{
    public static Function<Flux<PurchaseOrder>,Flux<PurchaseOrder>> automotiveProcessing(){
        return flux->flux.doOnNext(p->p.setPriced(1.1* p.getPriced())) //apply 10 percent tax
                .doOnNext(p->p.setItem("{{ "+p.getItem()+" }}"));
    }
    public static Function<Flux<PurchaseOrder>,Flux<PurchaseOrder>> kidsProcessing(){
        return flux->flux.doOnNext(p->p.setPriced(0.5*p.getPriced())) //apply 5 percent tax
                .doOnNext(p->p.setItem("{{ "+p.getItem()+" }}"))
                .flatMap(p->Flux.just(p,getFreeKidsOrder()));
    }

    private static PurchaseOrder getFreeKidsOrder(){
        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setItem("Free- "+purchaseOrder.getItem());
        purchaseOrder.setPriced(0.0);
        purchaseOrder.setCategory("Kids");
        return purchaseOrder;

    }

}
