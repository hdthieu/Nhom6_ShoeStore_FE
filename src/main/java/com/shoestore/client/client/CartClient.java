package com.shoestore.client.client;

import com.shoestore.client.dto.request.CartDTO;
import com.shoestore.client.dto.request.CartItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cart-service", url = "http://localhost:8765")
public interface CartClient {

    @PostMapping("/cart/add")
    void addCartItem(@RequestBody CartItemDTO cartItemDTO);
    @GetMapping("/cart/userid/{userId}")
    CartDTO getCartByUserId(@PathVariable("userId") int userId);
    @DeleteMapping("/cart/delete/{cartId}/{productDetailId}")
    void deleteCartItem(@PathVariable("cartId") int cartId,
                        @PathVariable("productDetailId") int productDetailId);
}
