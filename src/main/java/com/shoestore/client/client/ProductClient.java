package com.shoestore.client.client;

import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.dto.request.ProductDetailDTO;
import com.shoestore.client.dto.request.VoucherDTO;
import com.shoestore.client.dto.response.ProductSingleResponseDTO;

import feign.Request;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8765")
//@FeignClient(name = "PRODUCT-SERVICE", url = "http://api-gateway:8765")

public interface ProductClient {

    // Lấy ProductDetail theo productDetailId
    @GetMapping("/products-details/productDetailId/{id}")
    ProductDetailDTO getProductDetailById(@PathVariable("id") Integer id);

    // Lấy danh sách ProductDetail theo ProductID (trả về Map với key = "productDetails")
    @GetMapping("/products-details/{productId}")
    Map<String, List<ProductDetailDTO>> getProductDetailsByProductId(@PathVariable("productId") Integer productId);

    // Lấy thông tin Product (gọi endpoint khác nếu cần chi tiết)
    @GetMapping("products/detailFor/{id}")
    ProductDTO getProductById(@PathVariable("id") Integer id);

    @GetMapping("/products/voucher/check")
    VoucherDTO checkVoucherByCode(@RequestParam("code") String code);



}


