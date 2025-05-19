package com.shoestore.client.service.impl;

import com.shoestore.client.client.ProductClient;
import com.shoestore.client.dto.request.*;
import com.shoestore.client.dto.response.*;
import com.shoestore.client.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shoestore.client.dto.response.OrderResponseDTO;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @Override
    public Map<String, Object> getRevenueStatistics(String startDate, String endDate) {
        String url = "http://localhost:8765/Order/revenue?startDate=" + startDate + "&endDate=" + endDate;
        return restTemplate.getForObject(url, Map.class);
    }

    public List<OrderDTO> getOrdersFromServer() {
        String url = "http://localhost:8765/Order/dsachOrders";
        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public Page<OrderDTO> getOrdersWithPagination(Pageable pageable) {
        List<OrderDTO> orders = getOrdersFromServer();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), orders.size());
        return new PageImpl<>(orders.subList(start, end), pageable, orders.size());
    }

    @Override
    public List<BestSellerDTO> getTopSellingProducts(String type) {
        String url = String.format("http://localhost:8765/Order/bestsellers?type=%s&limit=5", type);
        try {
            ResponseEntity<List<BestSellerDTO>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> getYearlyRevenue() {
        String url = "http://localhost:8765/Order/yearly-revenue";
        return restTemplate.getForObject(url, Map.class);
    }

    @Override
    public List<Map<String, Object>> getTop10LoyalCustomers() {
        String url = "http://localhost:8765/Order/loyal-customers?minOrders=";
        ResponseEntity<List> response = restTemplate.exchange(
                url, HttpMethod.GET, null, List.class
        );
        return response.getBody();
    }

    @Override
    public Map<String, Long> getOrderStatistics() {
        String url = "http://localhost:8765/Order/OrderStatistics";
        return restTemplate.getForObject(url, Map.class);
    }

    @Override
    public OrderCheckoutDTO addOrder(OrderCheckoutDTO orderCheckoutDTO) {
        String url = "http://localhost:8765/Order/add";
        return restTemplate.postForObject(url, orderCheckoutDTO, OrderCheckoutDTO.class);
    }

    @Override
    public OrderCheckoutDTO getById(int id) {
        String url = "http://localhost:8765/Order/" + id;
        ResponseEntity<OrderCheckoutDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, null, OrderCheckoutDTO.class
        );
        return response.getBody();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(int userId) {
        String apiUrl = "http://localhost:8765/Order/user/" + userId; // API để lấy danh sách đơn hàng theo userId

        try {
            ResponseEntity<List<OrderResponseDTO>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OrderResponseDTO>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to fetch orders: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching orders: " + e.getMessage(), e);
        }
    }

    @Override
    public Page<OrderDTO> findByStatus(String status, Pageable pageable) {
        String url = "http://localhost:8765/Order/searchStatus?status=" + status +
                "&page=" + pageable.getPageNumber() +
                "&size=" + pageable.getPageSize();

        ResponseEntity<PageDTO<OrderDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );

        PageDTO<OrderDTO> pageData = response.getBody();
        if (pageData == null || pageData.getContent() == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        return new PageImpl<>(
                pageData.getContent(),
                pageable,
                pageData.getTotalElements()
        );
    }
}
