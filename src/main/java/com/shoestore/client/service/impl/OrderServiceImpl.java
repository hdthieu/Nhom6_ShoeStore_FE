package com.shoestore.client.service.impl;

import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<String, Object> getRevenueStatistics(String startDate, String endDate) {
        String SERVER_URL = "http://localhost:8080/Order/revenue";
        String url = SERVER_URL + "?startDate=" + startDate + "&endDate=" + endDate;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }
    public List<OrderDTO> getOrdersFromServer() {
        String apiUrl = "http://localhost:8080/Order/dsachOrders";
        ResponseEntity<List<OrderDTO>> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<OrderDTO>>() {});

        return responseEntity.getBody();
    }

    public Page<OrderDTO> getOrdersWithPagination(Pageable pageable) {
        String apiUrl = "http://localhost:8080/Order/dsachOrders";
        ResponseEntity<List<OrderDTO>> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<OrderDTO>>() {});

        List<OrderDTO> orders = responseEntity.getBody();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), orders.size());
        List<OrderDTO> pageContent = orders.subList(start, end);
        return new PageImpl<>(pageContent, pageable, orders.size());
    }

    @Override
    public List<ProductDTO> getTopSellingProducts(String type) {
        String SERVER_API_URL = "http://localhost:8080/OrderDetail/top-selling";
        String url = String.format("%s?type=%s&limit=5", SERVER_API_URL, type);
        try {
            ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {}
            );
            List<ProductDTO> products = response.getBody();
            System.out.println("Products received: " + products.size());
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> getYearlyRevenue() {
        String url = "http://localhost:8080/Order/yearly-revenue";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return response;
    }

    @Override
    public List<Map<String, Object>> getTop10LoyalCustomers() {
        String url = "http://localhost:8080/Order/loyal-customers?minOrders=" ;
        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                List.class
        );
        return response.getBody();
    }

    @Override
    public Map<String, Long> getOrderStatistics() {
        String SERVER_API_URL = "http://localhost:8080/Order/OrderStatistics";
        ResponseEntity<Map> response = restTemplate.getForEntity(SERVER_API_URL, Map.class);
        return response.getBody();
    }


}
