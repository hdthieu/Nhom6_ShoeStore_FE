package com.shoestore.client.controllers;

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
        List<CartItemResponseDTO> cartItems = cartItemService.getCartItemsByCartId(userId);
        cartItems.forEach((item) -> {
            ProductDTO productDTO = productService.getProductByProductDetail(item.getId().getProductDetailId());
            ProductDetailDTO productDetailDTO = productDetailService.getProductDetailById(item.getId().getProductDetailId());
            item.setProductName(productDTO.getProductName());
            item.setProductImage(productDTO.getImageURL());
            item.setProductPrice(productDTO.getPrice());
            item.setColor(productDetailDTO.getColor());
            item.setSize(productDetailDTO.getSize());
            item.setProductId(productDTO.getProductID());
            item.setStockQuantity(productDetailDTO.getStockQuantity());
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

        CartItemDTO.IdDTO idDTO = new CartItemDTO.IdDTO(cartDTO.getCartID(), productDetailID);
        CartItemDTO existingCartItem = cartItemService.getCartItemById(idDTO);
        ProductDTO productDTO = productService.getProductByProductDetail(productDetailID);

        if (existingCartItem != null) {
            int updatedQuantity = existingCartItem.getQuantity() + quantity;
            double updatedSubTotal = updatedQuantity * productDTO.getPrice();
            existingCartItem.setQuantity(updatedQuantity);
            existingCartItem.setSubTotal(updatedSubTotal);
            cartItemService.updateCartItem(idDTO, existingCartItem);
        } else {
            CartItemDTO newCartItem = new CartItemDTO();
            newCartItem.setId(idDTO);
            newCartItem.setQuantity(quantity);
            double price = productDTO.getPrice();
            double subTotal = price * quantity;
            newCartItem.setSubTotal(subTotal);

            ProductDetailDTO productDetailDTO = productDetailService.getProductDetailById(productDetailID);
            newCartItem.setCart(cartDTO);
            newCartItem.setProductDetailDTO(productDetailDTO);
            cartItemService.addCartItem(newCartItem);
        }

        return "redirect:/customer/cart/show";
    }

    @DeleteMapping("/delete/{cartId}/{productDetailId}")
    public String deleteCartItem(@PathVariable("cartId") int cartId,
                                 @PathVariable("productDetailId") int productDetailId) {
        CartItemDTO.IdDTO id = new CartItemDTO.IdDTO(cartId, productDetailId);
        cartItemService.deleteCartItem(id);
        return "redirect:/customer/cart/show";
    }
}
