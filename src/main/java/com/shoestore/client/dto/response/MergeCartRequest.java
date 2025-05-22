package com.shoestore.client.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MergeCartRequest {
    private List<CartItemResponseDTO> guestCart;
    private int userId;
}

