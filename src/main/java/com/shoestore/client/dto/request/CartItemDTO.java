package com.shoestore.client.dto.request;

import com.shoestore.client.dto.response.CartItemResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
    @NoArgsConstructor
    public static class IdDTO {
        private int cartId;
        private int productDetailId;

        public IdDTO(int cartId, int productDetailId) {
            this.cartId = cartId;
            this.productDetailId = productDetailId;
        }
    }
    public CartItemResponseDTO toCartItemResponseDTO() {
        CartItemResponseDTO responseDTO = new CartItemResponseDTO();

        // Tạo và gán ID cho đối tượng Response
        CartItemResponseDTO.IdDTO responseId = new CartItemResponseDTO.IdDTO(
                this.id.getCartId(),
                this.id.getProductDetailId()
        );
        responseDTO.setId(responseId);

        // Sao chép các thuộc tính khác
        responseDTO.setQuantity(this.quantity);
        responseDTO.setSubTotal(this.subTotal);
        responseDTO.setProductDetailDTO(this.productDetailDTO);

        return responseDTO;
    }

}
