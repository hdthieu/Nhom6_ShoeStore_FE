package com.shoestore.client.controllers;

import com.shoestore.client.client.CartClient;
import com.shoestore.client.dto.request.*;
import com.shoestore.client.dto.response.CartItemResponseDTO;
import com.shoestore.client.service.CartItemService;
import com.shoestore.client.service.CartService;
import com.shoestore.client.service.ProductDetailService;
import com.shoestore.client.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private HttpSession session;
    @Autowired
    private CartClient cartClient;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");

    @GetMapping("/show")
    public String showCart(Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        int userId = user.getUserID();

        // Gọi cart-service để lấy giỏ hàng qua FeignClient
        CartDTO cartDTO = cartClient.getCartByUserId(userId);
        if (cartDTO == null || cartDTO.getCartItems() == null) {
            model.addAttribute("cartItems", List.of()); // Trả về danh sách rỗng nếu không có gì
            System.out.println("cartItems "+ List.of());
            return "page/Customer/Cart";
        }

        List<CartItemResponseDTO> cartItems = cartDTO.getCartItems();

        // Bổ sung thông tin sản phẩm cho từng item
        cartItems.forEach(item -> {
            ProductDTO productDTO = productService.getProductByProductDetail(item.getId().getProductDetailId());
            ProductDetailDTO productDetailDTO = productDetailService.getProductDetailById(item.getId().getProductDetailId());

            item.setProductName(productDTO.getProductName());
            System.out.println("productDTO.getProductName() " + productDTO.getProductName());
//            item.setProductImage(productDTO.getImageURL());
            item.setProductPrice(productDTO.getPrice());
            item.setColor(productDetailDTO.getColor());
            item.setSize(productDetailDTO.getSize());
            item.setProductId(productDTO.getProductID());
            item.setStockQuantity(productDetailDTO.getStockQuantity());
            if (productDTO.getImageURL() != null && !productDTO.getImageURL().isEmpty()) {
                item.setProductImage(productDTO.getImageURL());  // ✅ Lấy đúng 1 ảnh đầu tiên
            }

        });

        model.addAttribute("cartItems", cartItems);
        return "page/Customer/Cart";
    }


    @PostMapping("/add")
    public String addCartItem(@RequestParam("productDetailID") int productDetailID,
                              @RequestParam("quantity") int quantity,
                              Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        int userId = user.getUserID();
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        if (cartDTO == null) {
            // Nếu giỏ hàng chưa tồn tại, xử lý tùy vào logic hệ thống của bạn
            return "redirect:/customer/cart/show";
        }

        CartItemDTO.IdDTO idDTO = new CartItemDTO.IdDTO(cartDTO.getCartID(), productDetailID);

        // Tạo CartItemDTO mới
        CartItemDTO newCartItem = new CartItemDTO();
        newCartItem.setId(idDTO);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cartDTO);
        newCartItem.setProductDetailDTO(productDetailService.getProductDetailById(productDetailID));

        try {
            cartClient.addCartItem(newCartItem);  // Gọi tới BE qua Feign
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi gọi CartClient.addCartItem: " + e.getMessage());
        }

        return "redirect:/customer/cart/show";
    }

    @DeleteMapping("/delete/{cartId}/{productDetailId}")
    public String deleteCartItem(@PathVariable("cartId") int cartId,
                                 @PathVariable("productDetailId") int productDetailId) {
        cartClient.deleteCartItem(cartId, productDetailId); // Gọi qua Feign
        return "redirect:/customer/cart/show";
    }
}
