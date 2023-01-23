package com.rp.assignment.flux;

import com.rp.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FileReaderService {

    private Callable<BufferedReader> openReader(Path path){
        return ()-> Files.newBufferedReader(path);
    }
    //first 2 input,3rd return value
    private BiFunction<BufferedReader, SynchronousSink<String>,BufferedReader> read(){
        return (br,sink)->{
            try {
                String line=br.readLine();
                if(Objects.isNull(line)){
                    sink.complete();
                }else {
                    sink.next(line);
                }

            }catch (Exception ex){
                sink.error(ex);
            }
            return br;
        };
    }

    //in callable we have thorws not in consumer lamda

    private Consumer<BufferedReader> closeReader(){
        return br->{
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
    public Flux<String> read(Path path){

        return Flux.generate(
                openReader(path),
                read(),
                closeReader()
        );

    }

    public static void main(String[] args) {
        FileReaderService fileReaderService=new FileReaderService();
        Path path= Paths.get("src/main/java/com/rp/assignment/flux/file01.txt");
        fileReaderService.read(path)
                .take(5)
                .log()
                .subscribe(Util.subscriber());
    }
}
