package com.rp.hotndcold;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class L04HotPublishAutoConnect {

    public static void main(String[] args) {
        //same content to both
        Flux<String> moveStream = Flux.fromStream(() -> getMovie())
                .delayElements(Duration.ofSeconds(1))
                .publish() //ConnectableFlux multiple subscriber connect at once
                .autoConnect(0); //minimumber subscriber if 2 so min 2 join play the movie

        //but not reconnect when other join after a time
        //no min requirement for subscriber as 0, so sam join late and see starting from scene 4
        Util.sleepSeconds(3);

        moveStream.subscribe(Util.subscriber("Sam: "));
        Util.sleepSeconds(10);
        moveStream.subscribe(Util.subscriber("Mike"));

        Util.sleepSeconds(50);
    }

    //Theater
    private static Stream<String> getMovie() {
        System.out.println("got the movie stream req");
        return Stream.of(
                "Scene ",
                "Scene 2",
                "Scene 3",
                "Scene 4",
                "Scene 5",
                "Scene 6",
                "Scene 7",
                "Scene 8"
        );
    }
}
