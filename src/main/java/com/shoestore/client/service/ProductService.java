package com.shoestore.client.service;



import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.dto.request.ProductHomeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    List<ProductDTO> getAllProduct();
    ProductDTO getProductByProductDetail(int id);
    public ProductDTO addProduct(ProductDTO productDTO);
    public ProductDTO getProductByIdForDetail(int id);
    //  Home
    public List<ProductHomeDTO> getTop10BestSellers();

    public List<ProductHomeDTO> getTop10NewArrivals();

    public List<ProductHomeDTO> getTop10Trending();
    List<ProductDTO> getFilteredProducts(List<Integer> category, List<Integer> brand, List<String> color, List<String> size, Double minPrice, Double maxPrice, String sortBy);

}
