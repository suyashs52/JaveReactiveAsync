package com.rp.hotndcold;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class L01ColdPublisher {

    public static void main(String[] args) {
        //same content to both
        Flux<String> moveStream = Flux.fromStream(() -> getMovie())
                .delayElements(Duration.ofSeconds(2));

        moveStream.subscribe(Util.subscriber("Sam: "));
        Util.sleepSeconds(5);
        moveStream.subscribe(Util.subscriber("Mike"));

        Util.sleepSeconds(50);
    }

    //netflix
    private static Stream<String> getMovie(){
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
