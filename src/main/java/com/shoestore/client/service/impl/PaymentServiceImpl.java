package com.shoestore.client.service.impl;

import com.shoestore.client.dto.request.PaymentDTO;
import com.shoestore.client.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    private final String PAYMENT_API_URL = "http://localhost:8765/payment/add"; // Có thể đưa vào application.properties
//    private final String PAYMENT_API_URL = "http://api-gateway:8765/payment/add"; // Có thể đưa vào application.properties

    @Override
    public PaymentDTO addPayment(PaymentDTO paymentDTO) {
        try {
            ResponseEntity<PaymentDTO> response = restTemplate.postForEntity(
                    PAYMENT_API_URL,
                    paymentDTO,
                    PaymentDTO.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("✔ Payment gửi thành công: " + response.getBody());
                return response.getBody();
            } else {
                System.err.println("❌ Lỗi khi gửi payment. Mã HTTP: " + response.getStatusCode());
                return null;
            }

        } catch (Exception e) {
            System.err.println("❌ Exception khi gửi payment: " + e.getMessage());
            return null;
        }
    }
}
