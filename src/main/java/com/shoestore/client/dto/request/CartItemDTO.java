package com.shoestore.client.dto.request;

import com.shoestore.client.dto.response.CartItemResponseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {
    private IdDTO id;
    private int quantity;
    private double subTotal;
    private CartDTO cart;
    private ProductDetailDTO productDetailDTO;

    public CartItemDTO(IdDTO id) {
        this.id = id;
    }



    @Data
    public static class IdDTO {
        private int cartId;
        private int productDetailId;

        public IdDTO(int cartId, int productDetailId) {
            this.cartId = cartId;
            this.productDetailId = productDetailId;
        }

        public IdDTO() {

        }
    }
}
