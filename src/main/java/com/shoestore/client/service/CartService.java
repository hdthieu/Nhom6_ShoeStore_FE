package com.shoestore.client.service;

import com.shoestore.client.dto.request.CartDTO;

public interface CartService {
    CartDTO getCartById(int id);
}
