package com.rp.context;

import com.rp.util.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class L01Context {

    public static void main(String[] args) {
        //when got user name the send message
        //context is map key value pair
        //move from bottom to up
        getWelcomeMessage()
                .contextWrite(ctx->ctx.put("user",ctx.get("user").toString().toUpperCase())) //almost like a cookie
                .contextWrite(Context.of("user","jake")) //almost like a cookie
                .contextWrite(Context.of("user","sam")) //almost like a cookie
                .contextWrite(Context.of("users","mon")) //almost like a cookie
                .subscribe(Util.subscriber());
    }

    private static Mono<Object> getWelcomeMessage(){
        //return Mono.fromSupplier(()->"Welcome");


        return  Mono.deferContextual(ctx->{
          if(  ctx.hasKey("user")){
              return Mono.just("welcome "+ctx.get("user"));
          }else {
              return Mono.just(new RuntimeException("unauthenticalted"));
          }
        });

    }
}
