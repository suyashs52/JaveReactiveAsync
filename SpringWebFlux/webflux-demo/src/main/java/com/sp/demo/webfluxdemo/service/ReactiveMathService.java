package com.sp.demo.webfluxdemo.service;

import com.sp.demo.webfluxdemo.dto.MultiplyRequestDto;
import com.sp.demo.webfluxdemo.dto.Response;
import org.springframework.http.codec.json.AbstractJackson2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(()->input*input)
                .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input){
        AbstractJackson2Encoder abstractJackson2Encoder;

        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1)) //non blocking,when subscriber stop this stop, close the browser
               // .doOnNext(i->Util.sleepSecond(1)) //blocking
                .doOnNext(i-> System.out.println("reactive-math-processing: "+i))
                .map(i->new Response(i*input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono){
        return dtoMono
                .map(dto->dto.getFirst()*dto.getSecond())
                .map(Response::new);
    }
}
