package com.demo.client.rpctypes;

import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.game.Die;
import com.grpc.game.GameState;
import com.grpc.game.Player;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GameStateStreamResponse implements StreamObserver<GameState> {

    CountDownLatch latch;
    private StreamObserver<Die> dieStreamObserver;

    public GameStateStreamResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(GameState gameState) {
        List<Player> list = gameState.getPlayerList();
        list.forEach(p -> System.out.println(p.getName() + ":" + p.getPosition()));
        boolean isGameOver = list.stream()
                .anyMatch(player -> player.getPosition() == 100);
        if (isGameOver) {
            System.out.println("Game Over!");
            dieStreamObserver.onCompleted();
        } else {
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            this.roll();
        }
        System.out.println("---------");
    }

    @Override
    public void onError(Throwable throwable) {
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        latch.countDown();
    }

    public void setDieStreamObserver(StreamObserver<Die> streamObserver) {
        this.dieStreamObserver = streamObserver;
    }

    public void roll() {
        int dieValue = ThreadLocalRandom.current().nextInt(1, 7);
        Die die = Die.newBuilder().setValue(dieValue).build();
        this.dieStreamObserver.onNext(die);
    }
}
