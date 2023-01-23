package com.rp.flux.create;

import com.rp.util.NameProducer;
import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L02FluxCreateRefactor {

    public static void main(String[] args) {

        NameProducer nameProducer=new NameProducer(); //type of consumer of fluxsink
      // nameProducer.produce();//flux create take consumer of type producer
        //create will call accept method of consumer when consumer subscribe
        //and we are setting fluxSink producer that automatically create inside create method
        //when we call consumer produce, so subsciber onNext method ll be called
        //from after queue poll from fromsink.next() this ll add entry in queue
        //that ll set and produce ll call subscriber onNext from drain method
        Flux.create(nameProducer).subscribe(Util.subscriber());
      //  Flux.create(nameProducer).subscribe(o-> System.out.println("Received11 : "+o));
        nameProducer.produce();
       Runnable runnable=()-> nameProducer.produce();
       //runnable-nameProducer::produce

        for (int i=0;i<10;i++){
            new Thread(runnable).start();//10 thread invoking this produce method
        }
    //so 10 thread doning this
        Util.sleepSeconds(3);
    }
}
