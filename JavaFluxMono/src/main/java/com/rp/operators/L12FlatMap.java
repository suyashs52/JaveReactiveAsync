package com.rp.operators;

import com.rp.util.Util;
import com.rp.util.entity.OrderService;
import com.rp.util.entity.UserService;

public class L12FlatMap {

    public static void main(String[] args) {
        //flatmap combine 2 or more flux and give one flux
        //flat map internally convert multiple flux  to single flux
        //order many not be in syn order
        //for sequetial flux execution use ConcatMap
        UserService.getUsers()
               // .map(user-> OrderService.getOrder(user.getUserId())) //return flux
                .flatMap(user-> OrderService.getOrder(user.getUserId())) //return flux
                .filter(i->Float.valueOf( i.getPrice())>30)
                .doOnDiscard(Object.class,i-> System.out.println("discared "+i))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(50);
    }
}
