package com.shoestore.client.service.impl;

import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private RestTemplate restTemplate;
    private static final String SERVER_API_URL = "http://localhost:8080/OrderDetail/layTT/";

    // Hàm gọi API server để lấy thông tin chi tiết đơn hàng
    public Map<String, Object> fetchOrderDetailByOrderID(int orderID) {
        String url = SERVER_API_URL + orderID;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }


//    public List<ProductDTO> getAvailableProducts(int orderID) {
//        String productApiUrl = "http://localhost:8080/OrderDetail/addProductToOrder/";
//        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
//                productApiUrl + orderID,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ProductDTO>>() {}
//        );
//
//        return response.getBody();
//    }

}
