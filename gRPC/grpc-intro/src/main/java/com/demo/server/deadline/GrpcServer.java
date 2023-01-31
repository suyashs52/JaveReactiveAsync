package com.demo.server.deadline;

import com.demo.game.GameService;
import com.demo.server.rpctypes.TransferService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(6565)
                .addService(new DeadlineService())

                .build();
        server.start();

        server.awaitTermination();//keep listening
    }
}
