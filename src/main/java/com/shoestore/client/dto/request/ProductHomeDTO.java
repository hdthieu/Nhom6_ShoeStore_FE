package com.shoestore.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductHomeDTO {
  private int productID;
  private String productName;
  private double price;
  private String status;
  private String description;
  private String brandName;
  private String categoryName;
  private LocalDateTime createDate;
  private long totalQuantity;
}

