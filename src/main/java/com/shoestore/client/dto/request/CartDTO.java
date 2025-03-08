package com.shoestore.client.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO {
  private int cartID;
//  private LocalDateTime createAt;
//  private List<CartItemResponseDTO> cartItems;
//  private UserDTO user;

  public CartDTO(int cartID) {
    this.cartID = cartID;
  }
}
