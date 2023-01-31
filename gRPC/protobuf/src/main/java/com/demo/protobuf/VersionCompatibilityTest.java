package com.demo.protobuf;

import com.demo.models.Television;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionCompatibilityTest {

    public static void main(String[] args) throws IOException {
//        Television.television television = Television.television.newBuilder()
//                .setBrand("sony")
//                .setYear(2015)
//                .build();
//
//        Path path= Paths.get("tv-v1");
//        Files.write(path,television.toByteArray());

        //deserialize
        Path path1= Paths.get("tv-v1");

        byte[] bytes=Files.readAllBytes(path1);
        System.out.println(
                Television.television.parseFrom(bytes)
        );


//
//        Path path3=Paths.get("tv-v1");
//
//        byte[] bytes=Files.readAllBytes(path1);
//        System.out.println(
//                Television.television.parseFrom(bytes)
//        );

    }
}
