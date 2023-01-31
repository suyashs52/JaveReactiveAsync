package com.demo.client.loadbalancing;

import com.demo.client.rpctypes.BalanceStreamObserver;
import com.grpc.models.Balance;
import com.grpc.models.BalanceChequeRequest;
import com.grpc.models.BankServiceGrpc;
import com.grpc.models.DepositRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class ClientSideLoadBalancingTest {
    //client side load balancing
    BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        //dont need nginx
        ServiceRegistry.register("bank-service", List.of(
                "localhost:6565"
                ,"localhost:7575"
        ));

        NameResolverRegistry.getDefaultRegistry().register(new TempNameResolverProvider());
        //creating channel
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forTarget("http://bank-service")
                .defaultLoadBalancingPolicy("round_robin")
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
        //run on single
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
