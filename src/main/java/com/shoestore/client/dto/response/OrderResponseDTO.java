package com.shoestore.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {
    private int orderID;
    private String orderDate;
    private String status;
    private double feeShip;
    private int voucherID;
    private int paymentID;
    private String shippingAddress;
    private int userID;
    private List<OrderDetailResponseDTO> orderDetails;
}