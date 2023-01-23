package com.rp.flux;

import com.rp.util.NameGenerator;
import com.rp.util.Util;

import java.util.List;

public class L07FluxvsList {

    public static void main(String[] args) {
        List<String> names = NameGenerator.getNames(5);//can you generate 5 name
        System.out.println(names); //after 5 sec you ll get all 5 names

        NameGenerator.getNamesFlux(5)
                .subscribe(Util.onNext()); //in a sec send ready data flux send one by one once it is ready
    }
}
