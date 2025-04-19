package com.shoestore.client.controllers;


import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Order") // <<-- quan trọng để khớp với URL
public class OrderCustomerController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUser(@PathVariable Integer userId) {
        return orderService.getOrdersByUserId(userId);
    }
}

