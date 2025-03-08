package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.*;
import com.shoestore.client.service.BrandService;
import com.shoestore.client.service.CategoryService;
import com.shoestore.client.service.ProductService;
import com.shoestore.client.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private BrandService brandService;

    @GetMapping("/product-search/filtered")
    public String showProducts(
            @RequestParam(required = false) List<Integer> categoryIds,  // List category IDs
            @RequestParam(required = false) List<Integer> brandIds,     // List brand IDs
            @RequestParam(required = false) List<String> colors,         // List colors
            @RequestParam(required = false) List<String> sizes,          // List sizes
            @RequestParam(required = false) String  price,
            @RequestParam(required = false) String  sortBy,
            Model model) {
        Double minPrice = null;
        Double maxPrice = null;
        if (price != null) {
            switch (price) {
                case "under50":
                    minPrice = 0.0;
                    maxPrice = 50.0;
                    break;
                case "50-100":
                    minPrice = 50.0;
                    maxPrice = 100.0;
                    break;
                case "100-150":
                    minPrice = 100.0;
                    maxPrice = 150.0;
                    break;
                case "150-200":
                    minPrice = 150.0;
                    maxPrice = 200.0;
                    break;
                case "200-300":
                    minPrice = 200.0;
                    maxPrice = 300.0;
                    break;
                case "over300":
                    minPrice = 300.0;
                    maxPrice = Double.MAX_VALUE;  // Không giới hạn maxPrice
                    break;
            }
        }

        List<ProductDTO> products;

        // Kiểm tra xem có filter nào không
        if ((categoryIds == null || categoryIds.isEmpty()) &&
                (brandIds == null || brandIds.isEmpty()) &&
                (colors == null || colors.isEmpty()) &&
                (sizes == null || sizes.isEmpty()) &&
                minPrice == null && maxPrice == null && sortBy ==null)
        {
            // Nếu không có filter, lấy toàn bộ sản phẩm
            products = productService.getAllProduct();
        } else {
            // Nếu có filter, gọi service để lấy sản phẩm theo filters
            products = productService.getFilteredProducts(categoryIds, brandIds, colors, sizes, minPrice, maxPrice,sortBy);

        }
        System.out.println(products);
        // Thêm danh sách sản phẩm vào model
        model.addAttribute("products", products);

        // Lấy danh sách các category và brand để hiển thị filter options
        List<CategoryProductCountDTO> categories = categoryService.getCountProduct();
        model.addAttribute("categories", categories);
        List<BrandDTO> brands = brandService.getAllBrand();
        model.addAttribute("brands", brands);

        // Thêm các size và color options vào model
        SizeDTO[] size = SizeDTO.values();
        model.addAttribute("sizes", size);
        ColorDTO[] color = ColorDTO.values();
        model.addAttribute("colors", color);

        return "page/Customer/SearchFragment";
    }
    @GetMapping("/product-search")
    public String showProducts(

            Model model) {
        List<ProductDTO> products=productService.getAllProduct();
        model.addAttribute("products", products);
        List<CategoryProductCountDTO> categories = categoryService.getCountProduct();
        model.addAttribute("categories", categories);
        List<BrandDTO> brands = brandService.getAllBrand();
        model.addAttribute("brands", brands);

        // Thêm các size và color options vào model
        SizeDTO[] size = SizeDTO.values();
        model.addAttribute("sizes", size);
        ColorDTO[] color = ColorDTO.values();
        model.addAttribute("colors", color);

        return "page/Customer/Search";
    }
}
