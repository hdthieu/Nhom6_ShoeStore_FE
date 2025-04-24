package com.shoestore.client.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentRequestDTO {
    private int orderID;
    private LocalDate paymentDate;
    private String status; // "Completed", "Pending", "Failed"
}
