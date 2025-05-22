package com.shoestore.client.dto.request;



/*
    @author: Đào Thanh Phú
    Date: 11/22/2024
    Time: 3:09 PM
    ProjectName: Client
*/


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // ✅ Bỏ qua nếu field "product" không tồn tại
public class ProductDetailDTO {
    private int productDetailID;
    private String color;
    private String size;
    private int stockQuantity;
    private double price;
    private ProductDTO product;
    private int productID;
}
