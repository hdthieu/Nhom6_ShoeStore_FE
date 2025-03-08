package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.UserDTO;
import com.shoestore.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class LoginController {

//  @Autowired
//  private UserService userService;
//  @Autowired
//  private CustomUserDetailService customUserDetailService;
//
//  // Đăng nhập
//  @GetMapping("/login")
//  public String login() {
//    return "page/Login";  //
//  }
//
//  @PostMapping("/login/auth")
//  public String login(@ModelAttribute UserDTO userDTO, Model model) {
//    UserDTO user = userService.findByEmail(userDTO.getEmail());
//    System.out.println(user);
//
//    // Kiểm tra xem người dùng có tồn tại và mật khẩu có khớp không
//    if (user != null && customUserDetailService.checkPassword(userDTO.getPassword(), user.getPassword())) {
//      System.out.println(1232);
//
//      if ("Admin".equals(user.getRole().getName())) {
//        return "page/Admin/TrangChuQuanLy";
//      } else if ("Customer".equals(user.getRole().getName())) {
//        System.out.println(user.getRole().getName());
//        return "page/Customer/Home";
//      }
//    }
//    return "redirect:/page/Login?error";
//  }
//
//
//  @GetMapping("/register")
//  public String showSignUpForm() {
//    return "page/Register";
//  }
//  @PostMapping("/register/auth")
//  public String registerUser(@ModelAttribute UserDTO userDTO, Model model) {
//    try {
//      userService.save(userDTO);
//      return "redirect:/login";
//    } catch (IllegalArgumentException e) {
//      model.addAttribute("error", e.getMessage());
//      return "page/Register";
//    }
//  }


}
