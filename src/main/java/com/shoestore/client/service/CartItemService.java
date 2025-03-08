package com.shoestore.client.service;

import com.shoestore.client.dto.request.CartItemDTO;
import com.shoestore.client.dto.response.CartItemResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {
  List<CartItemResponseDTO> getCartItemsByCartId(int cartId);
  CartItemDTO addCartItem(CartItemDTO cartItemDTO);
}
