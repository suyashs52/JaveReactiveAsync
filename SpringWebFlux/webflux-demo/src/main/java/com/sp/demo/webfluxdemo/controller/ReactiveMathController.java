package com.sp.demo.webfluxdemo.controller;


import com.sp.demo.webfluxdemo.dto.MultiplyRequestDto;
import com.sp.demo.webfluxdemo.dto.Response;
import com.sp.demo.webfluxdemo.service.MathService;
import com.sp.demo.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.AbstractJackson2Encoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rmath")

public class ReactiveMathController {

    //backen service  ll act as a publisheror processor, someone has to subscribe
    //browser is subscribing
    @Autowired
    private ReactiveMathService  mathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return this.mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        //in 10 sec we ll get the response
        AbstractJackson2Encoder abstractJackson2Encoder; //this take internally
        Mono<List<Response>> response; //type so after 10 sec it completed once flux emit all response
        //spring boot collect all the items convert it to json format , return whole object
        return this.mathService.multiplicationTable(input);
    }
    @GetMapping(value = "table/{input}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        //in 10 sec we ll get the response
        //json conversion happen for each item
        return this.mathService.multiplicationTable(input);
    }

//in request header we can't have mono,because we must know url before calling rest apiqœ
    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono
            ,@RequestHeader Map<String,String> headers){
        System.out.println(headers);
        return this.mathService.multiply(requestDtoMono);
    }
}
