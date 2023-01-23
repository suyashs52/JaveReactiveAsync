package com.rp.flux.create;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L07FluxGenerateCounter {

    public static void main(String[] args) {
        //for loop like init,condition:state,terminate
        Flux.generate(
                ()->1,
                (counter,sink)->{
                    String coutry= Util.faker().country().name();
                    System.out.println("emitting ..."+coutry);
                    sink.next(coutry);
                    if(counter>10 || coutry.toLowerCase().equals("canada")){
                        sink.complete();
                    }
                    return counter+1;
                }

                )
                .take(5)
                .subscribe(Util.subscriber());
    }
}
