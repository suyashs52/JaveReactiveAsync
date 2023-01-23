package com.rp.assignment.mono;

import com.rp.util.Util;

public class L01MonoFileFetch {

    public static void main(String[] args) {
        FileService.read("file01.txt")
                .subscribe(Util.onNext(), Util.onError()
                        , Util.onComplete()
                );
        FileService.write("file03.txt", "this is file03")
                .subscribe(Util.onNext(), Util.onError()
                        , Util.onComplete()
                );
        FileService.read("file04.txt")
                .subscribe(Util.onNext(), Util.onError()
                        , Util.onComplete()
                );
        FileService.delete("file03.txt")
                .subscribe(Util.onNext(), Util.onError()
                        , Util.onComplete()
                );
    }
}
