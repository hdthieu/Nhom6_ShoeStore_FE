package com.shoestore.client.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.service.OrderDetailService;
import com.shoestore.client.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller

@RequestMapping("/Order")
public class OrderCustomerController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
//    @GetMapping("/user/{userId}")
////    @ResponseBody
////    public List<OrderDTO> getOrdersByUser(@PathVariable Integer userId) {
////        return orderService.getOrdersByUserId(userId);
////    }
//    @GetMapping("/view/{orderID}")
//    public String viewOrderDetail(@PathVariable int orderID, Model model) {
//        Map<String, Object> orderDetail = orderDetailService.fetchOrderDetailByOrderID(orderID);
//        model.addAttribute("orderDetail", orderDetail);
//
//        return "page/Customer/OrderDetail";
//    }
}

