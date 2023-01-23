package com.rp.util;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;

import java.util.function.Consumer;

public class Util {

    private static final Faker FAKER=Faker.instance();
    public static Consumer<Object> onNext(){
        return o-> System.out.println("Received : "+o);
    }

   //consumer of type throwable accept parameter of type throwable  and don't return any thing
    public static Consumer<Throwable> onError(){
        return e-> System.out.println("Error : "+e.getMessage());
    }

    //runnable won't accept any parameter
    public static Runnable onComplete(){
        return ()-> System.out.println("Util Completed");
    }

    public static Faker faker(){
        return FAKER;
    }

    public static void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds*1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }


    public static Subscriber<Object> subscriber(){
        return new DefaultSubscriber();
    }
    public static Subscriber<Object> subscriber(String name){
        return new DefaultSubscriber(name);
    }

    public static void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
