package com.demo.server.loadbalancing;

import com.demo.server.rpctypes.AccoundDatabase;
import com.grpc.models.Balance;
import com.grpc.models.DepositRequest;
import io.grpc.stub.StreamObserver;

public class CashStreamingRequest implements StreamObserver<DepositRequest> {


    public CashStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    private StreamObserver<Balance> balanceStreamObserver;
    int accountBalance;
    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber=depositRequest.getAccountNumber();
        System.out.println(
                "Received cash deposit for "+accountNumber
        );
        int amount=depositRequest.getAmount();
        accountBalance = AccoundDatabase.addBalance(accountNumber, amount);


    }

    @Override
    public void onError(Throwable throwable) {

        System.out.println(throwable.getMessage());

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder().setAmount(accountBalance).build();
        balanceStreamObserver.onNext(balance);
        balanceStreamObserver.onCompleted();
    }
}
