package com.shoestore.client.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCheckoutDTO {
    private int orderID;
    private LocalDate orderDate;
    private String status;
    private double total;
    private double feeShip;
    private VoucherDTO voucher;
    private String shippingAddress;
    private UserDTO user;

    // Thêm getter rõ ràng nếu cần dùng ở chỗ order.getId() kiểu Long
    public int getId() {
        return (int) this.orderID;
    }
}

