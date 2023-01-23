package com.rp.mono;

import java.util.stream.Stream;

public class L02StreamDemo {

    //rp means reactive programming
    public static void main(String[] args) {
        Stream<Integer> stream=Stream.of(1)
                .map(i->{
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                    return i*2;
                });

        System.out.println(stream); //stream lazy doesnt do anything cause we havn't use terminate operator

        stream.forEach(System.out::println); //terminal operator

    }
}
