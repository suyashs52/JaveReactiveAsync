package com.demo.protobuf;

import com.demo.models.PersonOuterClass;
import com.google.protobuf.Int32Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonDemo {
        public static void main(String[] args) throws IOException {
            PersonOuterClass.Person sam = PersonOuterClass.Person.newBuilder()
                    .setAge(Int32Value.newBuilder().setValue(10).build())

                    .setName("same")
                    .build();

      PersonOuterClass.Person sam2= PersonOuterClass.Person.newBuilder()
              .setAge(Int32Value.newBuilder().setValue(32).build())

              .setName("same")
                    .build();

            System.out.println(sam.equals(sam2));
            System.out.println(sam==sam2);

            Path path= Paths.get("sam.ser"); //this is the place i want to write serializable
           // Files.write(path,sam2.toByteArray()); write to file

            byte[] bytes=Files.readAllBytes(path);
            PersonOuterClass.Person newsam= PersonOuterClass.Person.parseFrom(bytes);
            System.out.println(newsam);
        }
}
