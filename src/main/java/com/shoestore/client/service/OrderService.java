package com.shoestore.client.service;

import com.shoestore.client.dto.request.OrderCheckoutDTO;
import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.dto.response.BestSellerDTO;
import com.shoestore.client.dto.response.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    public List<OrderDTO> getOrdersFromServer();

    public Page<OrderDTO> getOrdersWithPagination(Pageable pageable);

    public Map<String, Object> getRevenueStatistics(String startDate, String endDate);

    public List<BestSellerDTO> getTopSellingProducts(String type);

    public Map<String, Object> getYearlyRevenue();

    public List<Map<String, Object>> getTop10LoyalCustomers();

    public Map<String, Long> getOrderStatistics();

    OrderCheckoutDTO addOrder(OrderCheckoutDTO orderCheckoutDTO);

    OrderCheckoutDTO getById(int id);

    List<OrderResponseDTO> getOrdersByUserId(int userId);

    public Page<OrderDTO> findByStatus(String status, Pageable pageable);
}