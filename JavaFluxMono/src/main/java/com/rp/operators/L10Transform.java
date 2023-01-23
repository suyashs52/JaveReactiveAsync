package com.rp.operators;

import com.rp.util.entity.Person;
import com.rp.util.Util;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class L10Transform {

    public static void main(String[] args) {
        getPerson()
                .transform(applyFilterMap())
                .subscribe(Util.subscriber());
    }

    public static Flux<Person> getPerson() {

       return Flux.range(1,10).map(i->new Person());

    }

    //function get and return
    public static Function<Flux<Person>,Flux<Person>> applyFilterMap(){
        return  personFlux -> personFlux.filter(p->p.getAge()>10)
                .doOnNext(p->p.setName(p.getName().toUpperCase()))
                .doOnDiscard(Person.class,p-> System.out.println("Not allowing: "+p)); //ignored items
    }
}
