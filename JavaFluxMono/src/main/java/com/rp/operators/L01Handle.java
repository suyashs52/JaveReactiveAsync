package com.rp.operators;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

public class L01Handle {

    public static void main(String[] args) {

        //handle=filter+map

        Flux.range(1,20)
                .handle((integer, synchronousSink) -> {
                    //two param, items and instance of sync sink
                    if(integer%2==0){ //filter
                        synchronousSink.next(integer);

                    }else if(integer==7){
                        synchronousSink.complete();
                    }
                    else {

                        synchronousSink.next(integer+"a"); //map
                    }

                })
                .subscribe(Util.subscriber());
    }
}
