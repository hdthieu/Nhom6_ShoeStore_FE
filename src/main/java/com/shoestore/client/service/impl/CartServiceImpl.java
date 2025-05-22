package com.shoestore.client.service.impl;

import com.shoestore.client.dto.request.CartDTO;
import com.shoestore.client.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CartDTO getCartByUserId(int userId) {
//        String BASE_GATEWAY_URL = "http://api-gateway:8765";
        String BASE_GATEWAY_URL = "http://localhost:8765";

        String url = BASE_GATEWAY_URL + "/cart/userid/" + userId;

        ResponseEntity<CartDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                CartDTO.class
        );

        return response.getBody();
    }
}
