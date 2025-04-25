package com.shoestore.client.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentResponseDTO {
    private int paymentID;
    private int orderID;
    private LocalDate paymentDate;
    private String status;
}

