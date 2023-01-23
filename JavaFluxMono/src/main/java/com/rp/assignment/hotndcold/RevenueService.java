package com.rp.assignment.hotndcold;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RevenueService {

    private Map<String, Double> db = new HashMap<>();

    public RevenueService() {
        db.put("Kid", 0.0);
        db.put("Automotive", 0.0);
    }

    public Consumer<PurchaseOrder> subscribeOrderStream() {
        //if key present sum the amount
        return p -> db.computeIfPresent(p.getCategory(), (k, v) ->
                v + p.getPrice()
        );
    }

    public Flux<String> revenueStream(){

        return Flux.interval(Duration.ofSeconds(2))
                .map(i->db.toString());

    }

}
