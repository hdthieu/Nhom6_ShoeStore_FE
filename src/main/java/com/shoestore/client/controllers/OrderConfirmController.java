package com.shoestore.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderConfirmController {

    @GetMapping("/order/confirm")
    public String confirmPage() {
        return "page/Customer/OrderConfirm"; // trỏ tới file templates/page/Customer/OrderConfirm.html
    }
}
