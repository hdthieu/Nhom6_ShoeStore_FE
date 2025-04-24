package com.shoestore.client.client;

import com.shoestore.client.dto.request.ProductDetailDTO;
import com.shoestore.client.dto.response.ProductDetailResponseDTO;
import com.shoestore.client.dto.response.ProductResponseDTO;
import com.shoestore.client.dto.response.ProductSingleResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8765/products")
public interface ProductClient {
    @GetMapping("/detailFor/{id}")
    ProductSingleResponseDTO getProductById(@PathVariable("id") Integer id);
    @GetMapping("/productDetailId/{id}")
    ProductDetailDTO getProductDetailById(@PathVariable("id") Integer id);
}

