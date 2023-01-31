package com.demo.game;

import com.grpc.game.Die;
import com.grpc.game.GameServiceGrpc;
import com.grpc.game.GameState;
import com.grpc.game.Player;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {

    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        Player client=Player.newBuilder().setName("client").setPosition(0).build();
        Player server =Player.newBuilder().setName("server").setPosition(0).build();

        return  new DieStreamingRequest(client,server,responseObserver);
    }
}
