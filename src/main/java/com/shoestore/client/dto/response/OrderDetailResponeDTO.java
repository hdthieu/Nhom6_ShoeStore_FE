package com.shoestore.client.dto.response;

import com.shoestore.client.dto.request.OrderCheckoutDTO;
import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.dto.request.ProductDetailDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrderDetailResponeDTO {
    private double price;
    private int quantity;
    private int productDetailId; // sửa từ ProductDetailDTO thành int
    private Long orderId;
    private String productName;
    // sửa từ OrderCheckoutDTO thành Long
}