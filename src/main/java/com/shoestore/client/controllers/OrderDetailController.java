package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.service.OrderDetailService;
import com.shoestore.client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/client/orderDetail")
public class OrderDetailController{
//    @Autowired
//    private OrderDetailService orderDetailService;
//
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping("/view/{orderID}")
//    public String viewOrderDetail(@PathVariable int orderID, Model model) {
//        Map<String, Object> orderDetail = orderDetailService.fetchOrderDetailByOrderID(orderID);
//        List<ProductDTO> availableProducts = orderDetailService.getAvailableProducts(orderID);
//        model.addAttribute("products",availableProducts);
//        model.addAttribute("orderDetail", orderDetail);
//        return "page/Admin/ChiTietDonHang";
//    }

}
