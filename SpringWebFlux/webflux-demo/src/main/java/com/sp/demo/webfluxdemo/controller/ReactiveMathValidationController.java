package com.sp.demo.webfluxdemo.controller;

import com.sp.demo.webfluxdemo.dto.Response;
import com.sp.demo.webfluxdemo.exception.InputValidationException;
import com.sp.demo.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RestController
@RequestMapping("rmath")
public class ReactiveMathValidationController {

    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) throw new InputValidationException(input);
        return this.mathService.findSquare(input);
    }

    @GetMapping("square/{input}/mono-throw")
    public Mono<Response> monoError(@PathVariable int input) {
        //whole thing happening in pipeline
        if (input < 10 || input > 20) throw new InputValidationException(input);
        return Mono.just(input).handle((integer, sink) -> { //handle filter and map
                    if (integer >= 10 && integer <= 20) {
                        sink.next(integer);
                    } else {
                        sink.error(new InputValidationException(integer));
                    }
                }).cast(Integer.class)
                .flatMap(i -> this.mathService.findSquare(i)) //to send item from mono
                ;
    }


    @GetMapping("square/{input}/mono-throw-a")
    public Mono<ResponseEntity<Response>> monoErrorWithoutGlobalError(@PathVariable int input) {
        //whole thing happening in pipeline
        int[] a={1,2,3};
        Arrays.sort(a);
        return Mono.just(input)
                .filter(i->i>=10 && i<=20)
                .flatMap(i->this.mathService.findSquare(i))
                .map( ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build()) ;//empty means filter out from filter
    }
}
