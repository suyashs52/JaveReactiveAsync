package com.rp.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class L03FluxFromArrayorList
{

    public static void main(String[] args) {
        List<String> str= Arrays.asList("a","b","c");

        Flux.fromIterable(str).subscribe((s)-> System.out.println(s));
        Flux.fromIterable(str).subscribe(Util.onNext());

        Flux.range(1,20).subscribe(Util.onNext());
    }
}
