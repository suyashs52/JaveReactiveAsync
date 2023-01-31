package com.demo.client.rpctypes;

import com.grpc.models.TransferRequest;
import com.grpc.models.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TransferClientTest {

    TransferServiceGrpc.TransferServiceStub stub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
       stub = TransferServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void transfer() throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(1);
        TransferStreamingResponse response=new TransferStreamingResponse(latch);
        StreamObserver<TransferRequest> requestStreamObserver =     stub.transfer(response);

        for (int i = 0; i < 100; i++) {
            TransferRequest transferRequest = TransferRequest.newBuilder()
                    .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(10 * ThreadLocalRandom.current().nextInt(1, 20))
                    .build();

            requestStreamObserver.onNext(transferRequest);

        }
        requestStreamObserver.onCompleted();
        latch.await();
    }

}
