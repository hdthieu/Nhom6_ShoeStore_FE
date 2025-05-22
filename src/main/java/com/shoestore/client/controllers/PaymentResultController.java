package com.shoestore.client.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentResultController {

    @GetMapping("/ordersuccess")
    public String orderSuccessPage() {
        return "page/Customer/ordersuccess"; // KHÔNG cần .html
    }

    @GetMapping("/orderfail")
    public String orderFailPage() {
        return "page/Customer/orderfail";
    }
}

