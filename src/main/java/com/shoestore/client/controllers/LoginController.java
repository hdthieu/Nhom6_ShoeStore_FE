package com.shoestore.client.controllers;

import com.shoestore.client.client.CartClient;
import com.shoestore.client.dto.request.CartDTO;
import com.shoestore.client.dto.request.CartItemDTO;
import com.shoestore.client.dto.request.UserDTO;
import com.shoestore.client.dto.response.CartItemResponseDTO;
import com.shoestore.client.security.CustomUserDetailService;
import com.shoestore.client.service.ProductDetailService;
import com.shoestore.client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;
  @Autowired
  private CustomUserDetailService customUserDetailService;
  @Autowired
  private HttpSession session;
  // Đăng nhập
  @GetMapping("/login")
  public String login() {
    return "page/Login";
  }

  @Autowired
  private CartClient cartClient;  // FeignClient gọi cart-service
  @Autowired
  private ProductDetailService productDetailService;

  @PostMapping("/login/auth")
  public String login(@ModelAttribute UserDTO userDTO, HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      UserDTO user = userService.findByEmail(userDTO.getEmail());

      if (user != null) {
        session.setAttribute("user", user);

        // Lấy giỏ hàng guest từ session
        List<CartItemResponseDTO> guestCart = (List<CartItemResponseDTO>) session.getAttribute("guestCart");
        if (guestCart != null && !guestCart.isEmpty()) {
          mergeCart(guestCart, user.getUserID());
          session.removeAttribute("guestCart");
        }

        String roleName = user.getRole().getName();
        if ("Admin".equals(roleName)) {
          return "redirect:/admin/orders/Home";  // Admin
        } else if ("Customer".equals(roleName)) {
          return "redirect:/customer/home";  // Customer
        }
      }
    }

    return "redirect:/login?error";
  }
  private CartItemDTO.IdDTO convertToCartItemIdDTO(CartItemResponseDTO.IdDTO responseId) {
    CartItemDTO.IdDTO dtoId = new CartItemDTO.IdDTO();
    dtoId.setCartId(responseId.getCartId());
    dtoId.setProductDetailId(responseId.getProductDetailId());
    return dtoId;
  }
  private void mergeCart(List<CartItemResponseDTO> guestCart, int userId) {
    // Lấy cart user hiện tại
    CartDTO userCart = cartClient.getCartByUserId(userId);

    if (userCart == null) {
      userCart = cartClient.createCartForUser(userId);
    }

    List<CartItemDTO> itemsToUpdate = new ArrayList<>();
    List<CartItemDTO> itemsToAdd = new ArrayList<>();

    Map<Integer, CartItemResponseDTO> userCartMap = new HashMap<>();
    if (userCart.getCartItems() != null) {
      for (CartItemResponseDTO item : userCart.getCartItems()) {
        userCartMap.put(item.getId().getProductDetailId(), item);
      }
    }

    for (CartItemResponseDTO guestItem : guestCart) {
      int pdId = guestItem.getId().getProductDetailId();
      if (userCartMap.containsKey(pdId)) {
        CartItemResponseDTO userItem = userCartMap.get(pdId);
        userItem.setQuantity(userItem.getQuantity() + guestItem.getQuantity());

        CartItemDTO updatedItem = new CartItemDTO();
        updatedItem.setId(convertToCartItemIdDTO(userItem.getId())); // Sử dụng phương thức chuyển đổi
        updatedItem.setQuantity(userItem.getQuantity());
        itemsToUpdate.add(updatedItem);
      } else {
        CartItemDTO newItem = new CartItemDTO();
        CartItemDTO.IdDTO idDTO = new CartItemDTO.IdDTO(userCart.getCartID(), pdId);
        newItem.setId(idDTO);
        newItem.setQuantity(guestItem.getQuantity());
        newItem.setCart(userCart);
        newItem.setProductDetailDTO(productDetailService.getProductDetailById(pdId));
        itemsToAdd.add(newItem);
      }
    }


    // Batch update và add
    if (!itemsToUpdate.isEmpty()) {
      cartClient.updateCartItemQuantity((CartItemResponseDTO) itemsToUpdate);
    }
    if (!itemsToAdd.isEmpty()) {
      for (CartItemDTO item : itemsToAdd) {
        cartClient.addCartItem(item);
      }
    }
  }




  @GetMapping("/register")
  public String showSignUpForm() {
    return "page/Register";
  }

  @PostMapping("/register/auth")
  public String registerUser(@ModelAttribute UserDTO userDTO, Model model) {
    try {
      userService.save(userDTO);
      return "redirect:/login";
    } catch (IllegalArgumentException e) {
      model.addAttribute("error", e.getMessage());
      return "page/Register";
    }
  }


}
