package com.sp.demo.webfluxdemo.service;

public class Util {

    public static void sleepSecond(int second){
        try {
            Thread.sleep(second*1000);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
    }
}
