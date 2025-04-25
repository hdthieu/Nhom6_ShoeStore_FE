package com.shoestore.client.dto.response;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSingleResponseDTO {
    private int productDetailId;
    private String productName;
    private double price;
    private String imageURL;
    // thêm các trường khác nếu cần
}