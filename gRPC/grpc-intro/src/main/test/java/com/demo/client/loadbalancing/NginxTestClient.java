package com.demo.client.loadbalancing;

import com.demo.client.rpctypes.BalanceStreamObserver;
import com.grpc.models.Balance;
import com.grpc.models.BalanceChequeRequest;
import com.grpc.models.BankServiceGrpc;
import com.grpc.models.DepositRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class NginxTestClient {
    //server side load balancing
    BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        //8585 is proxy port
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext()
                .build();
        System.out.println("Channel is created");
        this.bankServiceBlockingStub
                = BankServiceGrpc.newBlockingStub(managedChannel);  //blocking stubff
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
        System.out.println("Stubs are created.");
    }

    @Test
    public void balanceTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            BalanceChequeRequest balanceChequeRequest = BalanceChequeRequest
                    .newBuilder().setAccountNumber(ThreadLocalRandom.current().nextInt(1,11)).build();
          //  Thread.sleep(500);
            Balance balance = this.bankServiceBlockingStub.getBalance(balanceChequeRequest);
            System.out.println(
                    "Received:" + balance.getAmount()
            );
        }

    }


    @Test
    public void cashStreamingRequest() throws InterruptedException {
        //streaming means asyc call
        CountDownLatch latch=new CountDownLatch(1);
        StreamObserver<DepositRequest> depositRequestStreamObserver = bankServiceStub.cashDeposit(new BalanceStreamObserver(latch));
      //above assign the server so below streaming request goes to single server
        //suppose sending a file if failed in between doesnt go to next server remaining file
        for (int i = 0; i < 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();

            depositRequestStreamObserver.onNext(depositRequest);
        }

        depositRequestStreamObserver.onCompleted();
        latch.await();
    }
}
