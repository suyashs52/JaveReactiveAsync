package com.rp.util.entity;

import com.rp.util.Util;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder {

    private String item
            ;

    private  String price
            ;

    private double priced;
    private int userId;

    private String category;

    public PurchaseOrder(int userId) {
        this();
        this.userId = userId;

    }

    public PurchaseOrder() {
        this.item= Util.faker().commerce().productName();
        this.price=Util.faker().commerce().price();
        this.priced=Double.parseDouble(price);
        category=Util.faker().commerce().department();
    }
}
