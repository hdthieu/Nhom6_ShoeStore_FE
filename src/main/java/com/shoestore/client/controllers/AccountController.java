package com.shoestore.client.controllers;

import com.shoestore.client.client.OrderClient;
import com.shoestore.client.dto.request.AddressDTO;
import com.shoestore.client.dto.response.OrderResponseDTO;
import com.shoestore.client.dto.request.UserDTO;
import com.shoestore.client.service.AddressService;
import com.shoestore.client.service.OrderDetailService;
import com.shoestore.client.service.OrderService;
import com.shoestore.client.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class AccountController {

  @Autowired
  private HttpSession session;
  @Autowired
  private OrderClient orderClient;
  @Autowired private UserService userService;
  @Autowired private OrderService orderService;
  @Autowired private AddressService addressService;
  @Autowired
  private OrderDetailService orderDetailService;
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
    if (user == null) {
      return "redirect:/login";
    }

    try {
      // Gọi `OrderClient` để lấy danh sách đơn hàng
      List<OrderResponseDTO> allOrders = orderClient.getOrdersByUserId(user.getUserID());
      model.addAttribute("allOrders", allOrders); // Đẩy dữ liệu lên view
      System.out.println("allOrders: nè " + allOrders);
    } catch (Exception e) {
      // Log lỗi nếu xảy ra
      System.err.println("Error fetching orders: " + e.getMessage());
      model.addAttribute("error", "Unable to fetch orders at this time. Please try again later.");
    }

    return "page/Customer/MyOrders";
  }


  @GetMapping("/customer/account/my-account")
  public String showMyAccount(Model model) {
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null) return "redirect:/login";
    model.addAttribute("user", user);
    return "page/Customer/MyAccount";
  }

  @GetMapping("/customer/account/add-address")
  public String addAddress(Model model) {

    return "page/Customer/AddAddress";
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
  @GetMapping("/customer/account/view/{orderID}")
  public String viewOrderDetail(@PathVariable int orderID, Model model) {
    Map<String, Object> orderDetail = orderDetailService.fetchOrderDetailByOrderID(orderID);

    System.out.println("Order detail: " + orderDetail);
    // Log kết quả
    if (orderDetail == null || orderDetail.isEmpty()) {
      System.out.println("No details found for order ID: " + orderID);
      model.addAttribute("error", "Order details not found.");
      return "error-page"; // Trả về trang lỗi
    }


    model.addAttribute("orderDetail", orderDetail);
    return "page/Customer/OrderDetail";
  }


  @PostMapping("/customer/account/add-address")
  public String handleAddAddress(@ModelAttribute AddressDTO addressDTO) {
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null) return "redirect:/login";

    addressService.createAddress(user.getUserID(), addressDTO);
    return "redirect:/customer/account";
  }

}

