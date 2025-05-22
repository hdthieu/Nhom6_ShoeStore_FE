package com.shoestore.client.dto.response;

import com.shoestore.client.dto.request.ColorDTO;
import com.shoestore.client.dto.request.ProductDetailDTO;
import com.shoestore.client.dto.request.SizeDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



@Getter
@Setter

@NoArgsConstructor
public class CartItemResponseDTO {
  private IdDTO id;
  private int quantity;
  private double subTotal;
  private String productName;
  private List<String> productImage;
  private double productPrice;
  private ColorDTO color;
  private SizeDTO size;
  private int stockQuantity;
  private int productId;
  private ProductDetailDTO productDetailDTO;
  @Getter
  @Setter
  @NoArgsConstructor
  public static class IdDTO {
    private int cartId;
    private int productDetailId;

    public IdDTO(int cartId, int productDetailId) {
      this.cartId = cartId;
      this.productDetailId = productDetailId;
    }
  }
}
