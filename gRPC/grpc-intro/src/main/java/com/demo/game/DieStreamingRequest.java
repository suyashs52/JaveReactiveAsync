package com.demo.game;

import com.grpc.game.Die;
import com.grpc.game.GameState;
import com.grpc.game.Player;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DieStreamingRequest implements StreamObserver<Die> {

    private Player client;
    private Player server;

    private StreamObserver<GameState> gameStateStreamObserver;

    public DieStreamingRequest(Player client, Player server, StreamObserver<GameState> gameStateStreamObserver) {
        this.client = client;
        this.server = server;
        this.gameStateStreamObserver = gameStateStreamObserver;
    }

    @Override
    public void onNext(Die die) {
        this.client = getNewPlayerPosition(client, die.getValue());
        if (client.getPosition() != 100) {
            server = getNewPlayerPosition(server, ThreadLocalRandom.current().nextInt(1, 7));
        }
        gameStateStreamObserver.onNext(this.getGameState());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        gameStateStreamObserver.onCompleted();

    }


    private Player getNewPlayerPosition(Player player, int dieValue) {
        int position = player.getPosition() + dieValue;
        position=SnakesAndLadderMap.getPosition(position);
        if (position <= 100) {
            player = player.toBuilder()
                    .setPosition(position)
                    .build();
        }
        return player;
    }

    private GameState getGameState() {
        return GameState.newBuilder()
                .addPlayer(client)
                .addPlayer(server)
                .build();
    }
}
