package com.shoestore.client.dto.response;

import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.dto.request.UserDTO;
import com.shoestore.client.dto.request.VoucherDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailResponseDTO {
    private int orderID;
    private double feeShip;
    private String status;
    private String shippingAddress;
    private int voucherID;
    private VoucherDTO voucher;
    private UserDTO user;
    private List<OrderDTO> orderDetails;
    // getters and setters
}

