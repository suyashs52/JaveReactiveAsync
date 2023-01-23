package com.rp.mono;

import com.github.javafaker.Faker;

public class L04MonoFaker {

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            System.out.println(
            Faker.instance().name().fullName()) ;
        }
    }
}
