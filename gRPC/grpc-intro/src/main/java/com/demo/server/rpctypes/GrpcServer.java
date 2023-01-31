package com.demo.server.rpctypes;

import com.demo.game.GameService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
 // Streaming RPC: example: pagination of large data, receiving side might take longer time: file upload
    //More efficient than mulitple rpc cal,
    //Single RPC: size is small ,efficient than stream rpc
    public static void main(String[] args) throws InterruptedException, IOException {
        //channel for connection betwenn server and client,it is persistent, creating is lazy & establish during the first rpc
        //1 conneciton enough for communication for concurrent request, expensive process, c
        //out of memory/crashed/shutdown can close the connection,thread safe, can be share with muliple stube for the server

        //not a day connection just concurrent conection
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .addService(new TransferService())
                .addService(new GameService())
                .build();
        server.start();

        server.awaitTermination();//keep listening
    }
}
