package com.shoestore.client.controllers;

import com.shoestore.client.client.CartClient;
import com.shoestore.client.client.ProductClient;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private ProductClient productClient;
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
        CartDTO cartDTO = cartClient.getCartByUserId(userId);

        if (cartDTO == null || cartDTO.getCartItems() == null) {
            model.addAttribute("cartItems", List.of());
            return "page/Customer/Cart";
        }

        List<CartItemResponseDTO> cartItems = cartDTO.getCartItems();

        cartItems.forEach(item -> {
            int productDetailId = item.getId().getProductDetailId();

            try {
                System.out.println("➡️ Đang xử lý productDetailId = " + productDetailId);

                ProductDetailDTO productDetailDTO = productClient.getProductDetailById(productDetailId);
                System.out.println("📦 productDetailDTO = " + productDetailDTO);

                if (productDetailDTO == null) {
                    System.err.println("❌ productDetailDTO null cho ID: " + productDetailId);
                    return;
                }

                ProductDTO productDTO = productClient.getProductById(productDetailDTO.getProductID());

                item.setProductName(productDTO.getProductName());
                item.setProductImage(productDTO.getImageURL());
                item.setProductPrice(productDTO.getPrice());
                item.setProductId(productDTO.getProductID());
                item.setColor(ColorDTO.valueOf(productDetailDTO.getColor()));
                item.setSize(SizeDTO.valueOf(productDetailDTO.getSize()));

                System.out.println("📊 stockQuantity nhận từ BE = " + productDetailDTO.getStockQuantity());
                // nếu cần dùng trong template
                item.setStockQuantity(100);// gán ngay sau
                System.out.println("🧪 item class = " + item.getClass().getName());


                System.out.println("✅ Sau gán: item.stockQuantity = " + item.getStockQuantity());

            } catch (Exception e) {
                System.err.println("⚠️ Lỗi khi gọi ProductClient với productDetailId: " + productDetailId + " - " + e.getMessage());
            }
        });

// Sau khi xử lý tất cả item
        System.out.println("\n======== TOÀN BỘ CART ITEMS nè =========");
        cartItems.forEach(it ->
                System.out.println("🛒 [" + it.getProductName() + "] - stock = " + it.getStockQuantity())
        );

        model.addAttribute("cartItems", cartItems);
        return "page/Customer/Cart";

    }



    @PostMapping("/add")
    public String addCartItem(@RequestParam("productDetailID") int productDetailID,
                              @RequestParam("quantity") int quantity,
                              RedirectAttributes redirectAttributes) {
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
            return "redirect:/customer/cart/show";
        }

        CartItemDTO.IdDTO idDTO = new CartItemDTO.IdDTO(cartDTO.getCartID(), productDetailID);
        CartItemDTO newCartItem = new CartItemDTO();
        newCartItem.setId(idDTO);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cartDTO);
        newCartItem.setProductDetailDTO(productDetailService.getProductDetailById(productDetailID));

        try {
            cartClient.addCartItem(newCartItem);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Thêm vào giỏ hàng thành công!");
        } catch (Exception e) {
            e.printStackTrace(); // 👈 thêm dòng này để hiện lỗi rõ ràng
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Lỗi khi thêm vào giỏ hàng: " + e.getMessage());
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
