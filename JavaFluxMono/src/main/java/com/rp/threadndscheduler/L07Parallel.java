package com.rp.threadndscheduler;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class L07Parallel {

    public static void main(String[] args) {

        //publish by each publisher parallely use parallel,
        //publish and sub handle by multiple thread

            Flux.range(1,10)
                    .parallel()
                    .runOn(Schedulers.parallel())

                    .doOnNext(i -> printThreadName("next " + i))

                    .subscribe(v -> printThreadName("subs " + v));

        Util.sleepSeconds(5);

        //list is not thread safe  so lets element in list
        List<Integer> list=new ArrayList<>();
        Flux.range(1,100)
                .parallel(4) //split the task with 4 thread
                .runOn(Schedulers.parallel())
                //.sequential() convert this to sequenctial and we ll have subscribeOn
                .subscribe(v -> list.add(v));

        Util.sleepSeconds(3);
        System.out.println("list size is ....");
        System.out.println(list.size());
        System.out.println(list);



    }


    private static void printThreadName(String msg) {
        System.out.println(msg + " \t\t: Thread: " + Thread.currentThread().getName());
    }
}
