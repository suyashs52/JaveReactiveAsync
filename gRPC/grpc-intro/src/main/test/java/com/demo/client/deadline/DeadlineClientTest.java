package com.demo.client.deadline;

import com.demo.client.rpctypes.BalanceStreamObserver;
import com.demo.client.rpctypes.MoneyStreamResponse;
import com.grpc.models.*;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeadlineClientTest {
    BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .intercept(new DeadlineInterceptor())
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
        //we ll wait for 2 sec for response from server only if not throw the exception
        BalanceChequeRequest balanceChequeRequest = BalanceChequeRequest.newBuilder()
                .setAccountNumber(5).build();
        try {
            Balance balance = this.bankServiceBlockingStub
                 //   .withDeadline(Deadline.after(2, TimeUnit.SECONDS)) //already present in setup class
                    .getBalance(balanceChequeRequest);
            System.out.println(
                    "Received:" + balance.getAmount()
            );

        }catch (StatusRuntimeException ex){
            //go with default values
        }

    }

    @Test
    public void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(40).build();
        try {
            //server sending and working , client dead and remove from server
            this.bankServiceBlockingStub
                   // .withDeadline(Deadline.after(4,TimeUnit.SECONDS))
                    .withdraw(withdrawRequest)

                    .forEachRemaining(money -> {
                        System.out.println("Received : " + money.getValue());
                    });
        }catch (StatusRuntimeException ex){
            //
            System.out.println(ex.getMessage());
        }


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
