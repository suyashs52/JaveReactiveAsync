package com.demo.server.deadline;

import com.demo.server.rpctypes.AccoundDatabase;
import com.demo.server.rpctypes.CashStreamingRequest;
import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.models.*;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class DeadlineService  extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceChequeRequest request, StreamObserver<Balance> responseObserver) {

        int accountNumber=request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(AccoundDatabase.getBalance(accountNumber) )
                .build();

        //simulate time-consuming call
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        responseObserver.onNext(balance);

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
        //simulate time-consuming call
            Uninterruptibles.sleepUninterruptibly(3,TimeUnit.SECONDS);
            //if any body listening then send
            if(!Context.current().isCancelled()){
                responseObserver.onNext(money);
                System.out.println("Delivered $10");
                AccoundDatabase.deductBalance(accountNumber,10);
            }else {
                break;
            }


        }
        System.out.println("Completed");
        responseObserver.onCompleted();




    }

    //client multiple stream send and server responsed done only
    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
         //input of type StreamObserver of DepositRequest
        return new CashStreamingRequest(responseObserver);
    }



}
