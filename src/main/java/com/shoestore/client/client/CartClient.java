package com.shoestore.client.client;

import com.shoestore.client.dto.request.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service", url = "http://localhost:8765")
public interface CartClient {
    @GetMapping("/cart/userid/{userId}")
    CartDTO getCartByUserId(@PathVariable("userId") int userId);
    @DeleteMapping("/cart/delete/{cartId}/{productDetailId}")
    void deleteCartItem(@PathVariable("cartId") int cartId,
                        @PathVariable("productDetailId") int productDetailId);
}
