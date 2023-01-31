package com.demo.client.rpctypes;

import com.grpc.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {
    BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 7200)
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
        Thread.sleep(5000);
        BalanceChequeRequest balanceChequeRequest = BalanceChequeRequest.newBuilder().setAccountNumber(5).build();
        Balance balance = this.bankServiceBlockingStub.getBalance(balanceChequeRequest);
        System.out.println(
                "Received:" + balance.getAmount()
        );
    }

    @Test
    public void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(7).setAmount(40).build();
        this.bankServiceBlockingStub.withdraw(withdrawRequest)
                .forEachRemaining(money -> {
                    System.out.println("Received : " + money.getValue());
                });

    }


    @Test
    public void withdrawAysyncTest() throws InterruptedException {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(8).setAmount(40).build();
        CountDownLatch latch=new CountDownLatch(1);
        this.bankServiceStub.withdraw(withdrawRequest,new MoneyStreamResponse(latch));

    //    Uninterruptibles.sleepUninterruptibly(30, TimeUnit.SECONDS);
        latch.await();
    }

    @Test
    public void cashStreamingRequest() throws InterruptedException {
        //streaming means asyc call
        CountDownLatch latch=new CountDownLatch(1);
        StreamObserver<DepositRequest> depositRequestStreamObserver = bankServiceStub.cashDeposit(new BalanceStreamObserver(latch));
        for (int i = 0; i < 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();

            depositRequestStreamObserver.onNext(depositRequest);
        }

        depositRequestStreamObserver.onCompleted();
        latch.await();
    }
}
