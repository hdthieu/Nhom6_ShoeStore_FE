package com.shoestore.client.client;

import com.shoestore.client.dto.request.OrderCheckoutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDER-SERVICE", url = "http://localhost:8765/Order")
public interface OrderClient {

    @PostMapping("/create")
    OrderCheckoutDTO createOrder(@RequestBody OrderCheckoutDTO order);

    @GetMapping("/{id}")
    OrderCheckoutDTO getOrderById(@PathVariable("id") int id);
}
