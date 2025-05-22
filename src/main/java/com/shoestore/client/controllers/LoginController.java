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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/login/auth")
  public String login(@ModelAttribute UserDTO userDTO, HttpServletRequest request) {
    try {
      UsernamePasswordAuthenticationToken token =
              new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());

      Authentication authentication = authenticationManager.authenticate(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Đăng nhập thành công
      UserDTO user = userService.findByEmail(userDTO.getEmail());
      session.setAttribute("user", user);



      // Điều hướng theo vai trò
      String roleName = user.getRole().getName();
      if ("Admin".equals(roleName)) {
        return "redirect:/admin/orders/Home";
      } else {
        return "redirect:/customer/home";
      }

    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:/login?error=true";
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