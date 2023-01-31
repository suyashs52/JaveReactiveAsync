package com.demo.protobuf;

import com.demo.json.JPerson;
import com.demo.models.PersonOuterClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int32Value;
import com.google.protobuf.InvalidProtocolBufferException;

public class PerformanceTest {

    public static void main(String[] args) {

        //json
        JPerson person = new JPerson();
        person.setName("sam");
        person.setAge(10);
        ObjectMapper mapper = new ObjectMapper();
        Runnable json = () -> {

            try {
                byte[] bytes = mapper.writeValueAsBytes(person);
                System.out.println(bytes.length); //more value in byte from json
                JPerson person1 = mapper.readValue(bytes, JPerson.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };




        //protobuf
        PersonOuterClass.Person sam= PersonOuterClass.Person.newBuilder()
                .setName("sam")
                .setAge(Int32Value.newBuilder().setValue(32).build())

                .build();




        Runnable protobuf=()->{
            byte[] bytes=sam.toByteArray();
            try {
                System.out.println(bytes.length);
                PersonOuterClass.Person sam1= PersonOuterClass.Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 5; i++) {
            runPerformanceTest(json,"JSON");
            runPerformanceTest(protobuf,"PROTO");

        }


    }

    private static void runPerformanceTest(Runnable runnable, String method) {
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            runnable.run();
        }

        long time2 = System.currentTimeMillis();
        System.out.println((time2 - time1) + "ms");
    }


}
