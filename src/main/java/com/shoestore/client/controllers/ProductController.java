package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.BrandDTO;
import com.shoestore.client.dto.request.CategoryDTO;
import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.dto.request.SupplierDTO;
import com.shoestore.client.service.BrandService;
import com.shoestore.client.service.CategoryService;
import com.shoestore.client.service.ProductService;
import com.shoestore.client.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private SupplierService supplierService;
//    @Autowired
//    private BrandService brandService;
//
//    @GetMapping("/product")
//    public String listProductsAdmin(Model model){
//        List<ProductDTO> products= productService.getAllProduct();
//        model.addAttribute("products",products);
//        System.out.println(products);
//        return "page/Admin/QuanLySanPham";
//    }
//
//    @GetMapping("/product/add")
//    public String listCategoryAdmin(Model model){
//        List<CategoryDTO> categories= categoryService.getAllCategory();
//        model.addAttribute("categories",categories);
//        List<SupplierDTO> suppliers= supplierService.getAllSupplier();
//        model.addAttribute("suppliers",suppliers);
//        List<BrandDTO> brands= brandService.getAllBrand();
//        model.addAttribute("brands",brands);
//        System.out.println(categories);
//        return "page/Admin/ThemSanPham";
//    }
//
//    @PostMapping("/product/add")
//    public String addProduct(ProductDTO productDTO, Model model) {
//        try {
//            // Gửi sản phẩm mới đến server để lưu
//            ProductDTO savedProduct = productService.addProduct(productDTO);
//            model.addAttribute("message", "Sản phẩm đã được thêm thành công!");
//            return "redirect:/product"; // Chuyển hướng về danh sách sản phẩm
//        } catch (Exception e) {
//            model.addAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm: " + e.getMessage());
//            return "page/Admin/QuanLySanPham"; // Trả lại form thêm sản phẩm
//        }
//    }
//
//    @GetMapping("/detail/{id}")
//    public String showProductDetail(@PathVariable int id, Model model) {
//        ProductDTO productDTO = productService.getProductByIdForDetail(id);
//        model.addAttribute("product", productDTO);
//        return "page/Admin/ChiTietSanPham";
//    }
//
//    @GetMapping("/update/{id}")
//    public String showProductDetailUpdate(@PathVariable int id, Model model) {
//        ProductDTO productDTO = productService.getProductByIdForDetail(id);
//        model.addAttribute("product", productDTO);
//        List<CategoryDTO> categories= categoryService.getAllCategory();
//        model.addAttribute("categories",categories);
//        List<SupplierDTO> suppliers= supplierService.getAllSupplier();
//        model.addAttribute("suppliers",suppliers);
//        List<BrandDTO> brands= brandService.getAllBrand();
//        model.addAttribute("brands",brands);
//        return "page/Admin/SuaSanPham";
//    }

}
