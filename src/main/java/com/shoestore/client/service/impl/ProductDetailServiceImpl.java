package com.shoestore.client.service.impl;

import com.shoestore.client.client.ProductClient;
import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.dto.request.ProductDetailDTO;
import com.shoestore.client.dto.response.ProductDetailResponseDTO;
import com.shoestore.client.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductClient productClient;

    @Override
    public ProductDetailDTO getProductDetailById(int id) {
        return productClient.getProductDetailById(id);
    }

    @Override
    public List<ProductDetailDTO> getProductDetailByProduct(int productId) {
        Map<String, List<ProductDetailDTO>> response = productClient.getProductDetailsByProductId(productId);
        return response.get("productDetails");
    }
}
