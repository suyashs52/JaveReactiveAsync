package com.demo.client.rpctypes;

import com.grpc.game.Die;
import com.grpc.game.GameServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class GameClientTest {

    GameServiceGrpc.GameServiceStub stub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
       stub = GameServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void clientGame() throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(1);
        GameStateStreamResponse gameStateStreamResponse=new GameStateStreamResponse(latch);
        StreamObserver<Die> dieStreamObserver=this.stub.roll(gameStateStreamResponse);
        gameStateStreamResponse.setDieStreamObserver(dieStreamObserver);
        gameStateStreamResponse.roll();
        latch.await();
    }

}
