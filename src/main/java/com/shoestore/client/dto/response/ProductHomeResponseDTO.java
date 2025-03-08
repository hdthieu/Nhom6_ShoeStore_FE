package com.shoestore.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoestore.client.dto.request.ProductHomeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductHomeResponseDTO {
  private int totalItems;
  private int totalPages;
  @JsonProperty("bestSellers")
  private List<ProductHomeDTO> productDTOs;
}

