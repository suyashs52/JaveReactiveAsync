package com.rp.batching.helper;

import com.github.javafaker.Book;
import com.rp.util.Util;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookOrder {

    private String title;
    private String author;
    private String category;
    private double price;

    public BookOrder(){
        Book book= Util.faker().book();
        this.title=book.title();
        this.author=book.author();
        category=book.genre();
        price=Double.parseDouble(Util.faker().commerce().price());
    }
}
