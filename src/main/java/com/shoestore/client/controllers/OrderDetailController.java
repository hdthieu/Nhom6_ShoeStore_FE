package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.*;
import com.shoestore.client.service.BrandService;
import com.shoestore.client.service.CategoryService;
import com.shoestore.client.service.OrderDetailService;
import com.shoestore.client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/orderDetail")
public class OrderDetailController{
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/view/{orderID}")
    public String viewOrderDetail(@PathVariable int orderID, Model model) {
        Map<String, Object> orderDetail = orderDetailService.fetchOrderDetailByOrderID(orderID);
        model.addAttribute("orderDetail", orderDetail);

        return "page/Admin/ChiTietDonHang";     }


}
