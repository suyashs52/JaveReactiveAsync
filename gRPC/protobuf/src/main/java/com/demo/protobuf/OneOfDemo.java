package com.demo.protobuf;

import com.demo.models.Credentials;
import com.demo.models.EmailCredentials;
import com.demo.models.PhoneOTP;

public class OneOfDemo {

    public static void main(String[] args) {
        EmailCredentials emailCredentials=EmailCredentials
                .newBuilder().setEmail("nobody@gmail.com")
                .setPassword("admin123")
                .build();


        PhoneOTP phoneOTP=PhoneOTP.newBuilder()
                .setNumber(123456789)
                .setCode(435)
                .build();
//at last we are calling phone mode so phone mode ll be printed email detail has been erased
        Credentials credentials=Credentials.newBuilder()
                .setEmailMode(emailCredentials)
                .setPhoneMode(phoneOTP)
                .build();

        login(credentials);
    }

    private static void login(Credentials credentials){
        System.out.println(credentials.getModeCase());

        switch (credentials.getModeCase()){
            case EMAILMODE -> System.out.println(credentials.getEmailMode());
            case PHONEMODE -> System.out.println(credentials.getPhoneMode());

        }

        System.out.println(
                credentials.getEmailMode()
        );

        System.out.println(
                credentials.getPhoneMode()
        );
    }
}
