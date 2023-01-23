package com.rp.mono;

import com.rp.util.Util;
import reactor.core.publisher.Mono;

public class L05MonoEmptyOrError {
    public static void main(String[] args) {
        //this is subscriber
        //hey user do you have this data
        userRepository(1).subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );
//        System.out.println("id: 2..");
//        userRepository(2).subscribe(
//                Util.onNext(),
//                Util.onError(),
//                Util.onComplete()
//        );
        System.out.println("id: 3..");

        userRepository(3).subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );

        userRepository(4).subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );
    }

    private static Mono<String> userRepository(int userId){

        //1
        if(userId==1){
            return Mono.just(Util.faker().name().firstName());
        }else if(userId==2){
            return null;
        }else  if(userId==3){
            return Mono.empty(); //don't have data
        }else{
            return Mono.error(new RuntimeException("Not in the range"));
        }

    }
}
