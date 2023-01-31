package com.demo.server.loadbalancing;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer1 {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankService())

                .build();
        server.start();

        server.awaitTermination();//keep listening
    }
}
