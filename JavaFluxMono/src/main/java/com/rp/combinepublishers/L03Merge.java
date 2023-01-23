package com.rp.combinepublishers;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L03Merge {

    public static void main(String[] args) {
        //merge out result from multilple pblisher and send to subscriber
        //like airline websits takes coupon and show to users
        //merge eagerly:comes first merge first result first
        Flux<String> merge = Flux.merge(
                QatarFlights.getFlights(),
                EmiratesFlights.getFlights(),
                AmericanFlights.getFlights()
        );

        merge.subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }
}

class QatarFlights{
    public static Flux<String> getFlights(){
        return Flux.range(1, Util.faker().random().nextInt(1,5))
                .delayElements(Duration.ofSeconds(1))
                .map(i->"Quatar "+Util.faker().random().nextInt(100,999))
                .filter(i->Util.faker().random().nextBoolean())
                ;
    }
}
class EmiratesFlights{
    public static Flux<String> getFlights(){
        return Flux.range(1, Util.faker().random().nextInt(1,10))
                .delayElements(Duration.ofSeconds(1))
                .map(i->"Emirates "+Util.faker().random().nextInt(100,999))
                .filter(i->Util.faker().random().nextBoolean())
                ;
    }
}
class AmericanFlights{
    public static Flux<String> getFlights(){
        return Flux.range(1, Util.faker().random().nextInt(1,10))
                .delayElements(Duration.ofSeconds(1))
                .map(i->"AA "+Util.faker().random().nextInt(100,999))
                .filter(i->Util.faker().random().nextBoolean())
                ;
    }
}
