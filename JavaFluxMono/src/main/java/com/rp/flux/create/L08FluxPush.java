package com.rp.flux.create;

import com.rp.util.NameProducer;
import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L08FluxPush {

    public static void main(String[] args) {
        {

            NameProducer nameProducer=new NameProducer();
            //push is not thread safe ,create is thread safe,some result omit
            Flux.push(nameProducer)
                    .subscribe(Util.subscriber());
            nameProducer.produce();
            nameProducer.produce();


            Runnable run=nameProducer::produce;

            for (int i=0;i<10;i++){
                new Thread(run).start();
            }

            Util.sleepSeconds(2);


        }
    }
}
