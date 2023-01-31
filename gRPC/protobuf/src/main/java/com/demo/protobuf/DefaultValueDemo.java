package com.demo.protobuf;

import com.demo.models.PersonOuterClass;

public class DefaultValueDemo {

    public static void main(String[] args) {
        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder()
                .build();

        //get address is not null , giving default value
        System.out.println(person.getAddress().getCity());

        System.out.println(person.hasAddress());
    }
}
