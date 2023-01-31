package com.demo.protobuf;

import com.demo.models.*;
import com.google.protobuf.Int32Value;

import java.util.ArrayList;
import java.util.List;

public class CompositeDemo {

    public static void main(String[] args) {
         Address add =  Address
                .newBuilder()
                .setPosbox(123)
                .setCity("Atlanta")
                .build();

       Car accord =  Car.newBuilder()
                .setMake("honda")
                .setModel("accord")
                .setYear(2021)
                .build();
  Car civic = Car.newBuilder()
                .setMake("honda")
                .setModel("civic")
                .setYear(2005)
         .setBodyStyle(BodyStyle.COUPE)
                .build();



        PersonOuterClass.Person sam = PersonOuterClass.Person.newBuilder()
                .setName("sam")
                //.setAge(10)
                .setAge(Int32Value.newBuilder().setValue(32).build())
                .setAddress(add)
                .addCar(accord)
                .addCar(civic)
                .build();


        System.out.println(sam);

        Dealer dealer = Dealer.newBuilder()
                .putModel(2055, civic)
                .putModel(2020, accord)
                .build();

        dealer.getModelCount();
        System.out.println(dealer.getModelOrDefault(2023,accord));
        System.out.println(dealer.getModelCount());

        System.out.println(dealer.getModelOrThrow(2020).getBodyStyle());
        System.out.println(dealer.getModelOrThrow(2055).getBodyStyle());


    }
}
