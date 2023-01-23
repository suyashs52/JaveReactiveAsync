package com.rp.assignment.hotndcold;

import com.rp.util.Util;

public class Assignment {

    public static void main(String[] args) {
        OrderService orderService=new OrderService();
        RevenueService revenueService=new RevenueService();
        InventoryService inventoryService=new InventoryService();

        orderService.orderStream().subscribe(revenueService.subscribeOrderStream());
        orderService.orderStream().subscribe(inventoryService.subscribeOrderStream());

        inventoryService.inventoryStream().subscribe(Util.subscriber("inventory"));
        revenueService.revenueStream().subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(50);
    }
}
