package com.demo.server.loadbalancing;

import com.demo.server.rpctypes.AccoundDatabase;
import com.grpc.models.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService  extends BankServiceGrpc.BankServiceImplBase {
//client request in db, db send data
    @Override
    public void getBalance(BalanceChequeRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber=request.getAccountNumber();

        System.out.println(
                "Received the request for :"+accountNumber
        );
        Balance balanc = Balance.newBuilder()
                .setAmount(AccoundDatabase.getBalance(accountNumber) )
                .build();

        responseObserver.onNext(balanc);
      //   responseObserver.onNext(balanc); as it is one response so only one onNext()
        responseObserver.onCompleted();
    }

    //server streaming example
    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {

        int accountNumber=request.getAccountNumber();
        int amount=request.getAmount();
        int balance=AccoundDatabase.getBalance(accountNumber);

        if(balance <amount){
            Status status = Status.FAILED_PRECONDITION.withDescription("No enough money. You have only" + balance);

            responseObserver.onError(status.asRuntimeException());
            return;
        }

        //validation passed so deduction money as stream
        for (int i = 0; i < (amount / 10); i++) {
            Money money = Money.newBuilder().setValue(10).build();

            responseObserver.onNext(money);
            AccoundDatabase.deductBalance(accountNumber,10);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        responseObserver.onCompleted();




    }

    //client multiple stream send and server responsed done only
    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
         //input of type StreamObserver of DepositRequest
        return new CashStreamingRequest(responseObserver);
    }



}
