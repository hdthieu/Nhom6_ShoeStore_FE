package com.shoestore.client.client;

import com.shoestore.client.dto.request.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:8765/payment")
public interface PaymentClient {

    @PostMapping("/add")
    PaymentDTO createPayment(@RequestBody PaymentDTO payment);

    @GetMapping("/{id}")
    PaymentDTO getPaymentById(@PathVariable("id") int id);
}
