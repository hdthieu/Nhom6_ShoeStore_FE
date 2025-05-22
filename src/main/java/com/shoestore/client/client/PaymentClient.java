package com.shoestore.client.client;

import com.shoestore.client.dto.request.PaymentDTO;
import com.shoestore.client.dto.request.PaymentRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "payment-service", url = "http://localhost:8765")
//@FeignClient(name = "payment-service", url = "http://api-gateway:8765")

public interface PaymentClient {

    @PostMapping("/payment/add")
    PaymentDTO createPayment(@RequestBody PaymentRequestDTO payment);

    @GetMapping("/payment/{id}")
    PaymentDTO getPaymentById(@PathVariable("id") int id);

    @GetMapping("/payment/vnpay")
    String createVNPayUrl(@RequestParam("amount") int amount,
                          @RequestParam("orderInfo") String orderInfo,
                          @RequestParam("baseUrl") String baseUrl);

    @PostMapping("/payment/vnpay_return")
    int verifyReturn(@RequestBody Map<String, String[]> paramMap);
}

