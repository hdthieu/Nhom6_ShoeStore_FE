package com.shoestore.client.client;

import com.shoestore.client.dto.request.OrderCheckoutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "http://localhost:8765") // KHÔNG có /Order
public interface OrderClient {

    @PostMapping("/Order/add")
    OrderCheckoutDTO createOrder(@RequestBody OrderCheckoutDTO order);

    @GetMapping("/Order/{id}")
    OrderCheckoutDTO getOrderById(@PathVariable("id") int id);

    @PutMapping("/Order/updatePayment/{orderId}")
    void updatePaymentID(@PathVariable("orderId") int orderId, @RequestParam("paymentID") int paymentID);
}


