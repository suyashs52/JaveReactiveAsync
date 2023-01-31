package com.demo.client.rpctypes;

import com.grpc.models.Money;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class MoneyStreamResponse implements StreamObserver<Money> {

    CountDownLatch latch;
    public MoneyStreamResponse(CountDownLatch latch) {
        this.latch=latch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println(
                "Received async : "+money.getValue()
        );
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(
                throwable.getMessage()
        );
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println(
                "Server is done!!"
        );
        latch.countDown();
    }
}
