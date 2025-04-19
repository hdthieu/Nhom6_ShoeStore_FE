package com.shoestore.client.dto.request;

import lombok.Data;

@Data
public class OrderDetailRequestDTO {
    private double price;
    private int quantity;
    private int productDetailId;
    private int orderId;
}
