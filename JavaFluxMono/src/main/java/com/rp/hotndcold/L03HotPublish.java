package com.rp.hotndcold;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class L03HotPublish {

    public static void main(String[] args) {
        //same content to both
        Flux<String> moveStream = Flux.fromStream(() -> getMovie())
                .delayElements(Duration.ofSeconds(1))
                .publish() //ConnectableFlux multiple subscriber connect at once
                .refCount(1); //minimumber subscriber if 2 so min 2 join play the movie
        //if other join in a min it ll again get the same content from starting like cold publisher
        //but after 1 complete the watch if next subscriber joined , for intermediate both ll start sync
        //change the movie delay to 2 sec
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
