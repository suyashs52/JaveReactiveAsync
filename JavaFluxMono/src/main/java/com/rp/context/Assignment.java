package com.rp.context;

import com.rp.util.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Assignment {
    public static void main(String[] args) {
        //run from bottom to top
        //so get book last run first
        //has std attempt 2, sam has category std
        BookService.getBook()
                .repeat(10)//each time attemt -1 so
                .contextWrite(UserService.userCategoryContext())
                .contextWrite(Context.of("user","sam"))
                .subscribe(Util.subscriber());
    }
}

class BookService{
    private static  Map<String,Integer> map=new HashMap<>();

    static {
        map.put("std",2);
        map.put("prime",3);
    }

    public  static Mono<String> getBook(){
        return Mono.deferContextual(ctx->{
            if(ctx.get("allow")){
                return Mono.just(Util.faker().book().title());
            }else{
                return  Mono.error(new RuntimeException("not-allowed"));
            }
        }).contextWrite(rateLimitedContext());
    }
    private static Function<Context,Context> rateLimitedContext(){
        return ctx->{
            if(ctx.hasKey("category")){
                String category=ctx.get("category").toString();
                Integer attempts=map.get(category);
                if(attempts>0){
                    map.put(category,attempts-1);
                    return ctx.put("allow",true);
                }
            }
            return ctx.put("allow",false);
        };
    }
}
class UserService{
    private static Map<String,String> map=Map.of(
            "sam","std",
            "mike","prime"
    );

    public static Function<Context,Context> userCategoryContext(){
        return ctx->{
            String user=ctx.get("user").toString();
            String category=map.get(user);
            return ctx.put("category",category);
        };
    }


}
