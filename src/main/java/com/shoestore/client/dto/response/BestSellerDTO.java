package com.shoestore.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSellerDTO {
    private int productID;
    private long totalSold;
    private String productName;
    private String brandName;
    private String categoryName;
}

