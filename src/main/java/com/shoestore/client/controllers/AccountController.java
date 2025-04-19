package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.AddressDTO;
import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.dto.request.UserDTO;
import com.shoestore.client.service.AddressService;
import com.shoestore.client.service.OrderService;
import com.shoestore.client.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class AccountController {

  @Autowired
  private HttpSession session;

  @Autowired private UserService userService;
  @Autowired private OrderService orderService;
  @Autowired private AddressService addressService;

  @GetMapping("/customer/account")
  public String showAccount(Model model) {
    UserDTO user = (UserDTO) session.getAttribute("user");

    if (user != null) {
      model.addAttribute("userName", user.getName());
    } else {
      model.addAttribute("userName", "Guest");
    }

    return "/page/Customer/Account";
  }

  @GetMapping("/customer/account/my-orders")
  public String showMyOrders(Model model) {
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null) return "redirect:/login";

    List<OrderDTO> allOrders = orderService.getOrdersByUserId(user.getUserID());

    List<OrderDTO> processingOrders = allOrders.stream()
            .filter(order -> order.getStatus().equalsIgnoreCase("Processing"))
            .toList();

    List<OrderDTO> deliveredOrders = allOrders.stream()
            .filter(order -> order.getStatus().equalsIgnoreCase("Delivered"))
            .toList();

    model.addAttribute("processingOrders", processingOrders);
    model.addAttribute("deliverdOrders", deliveredOrders);

    return "page/Customer/MyOrders";
  }

  @GetMapping("/customer/account/my-account")
  public String showMyAccount(Model model) {
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null) return "redirect:/login";
    model.addAttribute("user", user);
    return "page/Customer/MyAccount";
  }

  @GetMapping("/customer/account/my-address")
  public String showAddress(Model model) {
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null) return "redirect:/login";

    List<AddressDTO> addressDTOList = addressService.getAddressByUserId(user.getUserID());
    List<String> formattedAddresses = addressDTOList.stream()
            .map(this::formatAddress)
            .toList();

    model.addAttribute("user", user);
    model.addAttribute("addresses", formattedAddresses);
    return "page/Customer/Address";
  }

  private String formatAddress(AddressDTO addressDTO) {
    return addressDTO.getStreet() + ", ward " + addressDTO.getWard() +
            ", district " + addressDTO.getDistrict() + ", " + addressDTO.getCity();
  }

  @PostMapping("/customer/account/update-account")
  public String updateAccount(@ModelAttribute UserDTO updatedUser) {
    UserDTO currentUser = (UserDTO) session.getAttribute("user");

    if (currentUser != null) {
      UserDTO updated = userService.updateUser(currentUser.getUserID(), updatedUser);
      session.setAttribute("user", updated);
    }

    return "redirect:/customer/account";
  }
}

