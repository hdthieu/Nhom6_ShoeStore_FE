package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.OrderDTO;
import com.shoestore.client.dto.request.ProductDTO;
import com.shoestore.client.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

//    @Autowired
//    private OrderService orderService;
//    @GetMapping("/loyal-customers")
//    @ResponseBody
//    public List<Map<String, Object>> getTop10LoyalCustomers() {
//        return orderService.getTop10LoyalCustomers();
//    }
//    @GetMapping("/Home")
//    public String showHomePage(Model model) {
//        String today = LocalDate.now().toString();
//        Map<String, Object> revenueStats = orderService.getRevenueStatistics(today, today);
//        Map<String, Object> revenueYear = orderService.getYearlyRevenue();
//        List<Map<String, Object>> loyalCustomer = orderService.getTop10LoyalCustomers();
//        Map<String, Long> statistics = orderService.getOrderStatistics();
//        List<ProductDTO> topSellingProducts = orderService.getTopSellingProducts("day");
//        model.addAttribute("totalRevenue", revenueStats.get("totalRevenue"));
//        model.addAttribute("totalOrders", revenueStats.get("totalOrders"));
//        model.addAttribute("startDate", today);
//        model.addAttribute("endDate", today);
//        model.addAttribute("totalRevenueYear", revenueYear.get("totalRevenue"));
//        model.addAttribute("totalOrdersYear", revenueYear.get("totalQuantity"));
//        model.addAttribute("loyalCustomer", loyalCustomer);
//        model.addAttribute("products", topSellingProducts);
//        model.addAttribute("statistics", statistics);
//        return "page/Admin/TrangChuQuanLy";
//    }
//
//    // thống kê sản phẩm bán chạy
//    @GetMapping("/top-selling")
//    @ResponseBody
//    public Map<String, Object> getTopSellingProducts(
//            @RequestParam String type,
//            @RequestParam(required = false, defaultValue = "0") int page,
//            @RequestParam(required = false, defaultValue = "5") int size) {
//        List<ProductDTO> products = orderService.getTopSellingProducts(type);
//        Map<String, Object> response = new HashMap<>();
//        response.put("products", products);
//        response.put("prevPage", page - 1);
//        response.put("nextPage", page + 1);
//        response.put("currentPage", page);
//        return response;
//    }
//
//    // thống kê doanh thu dựa trên hóa đơn
//    @GetMapping("/thong-ke")
//    @ResponseBody
//    public Map<String, Object> getRevenueStatistics(@RequestParam String startDate, @RequestParam String endDate) {
//        Map<String, Object> revenueStats = orderService.getRevenueStatistics(startDate, endDate);
//        return revenueStats;  // Trả về dữ liệu JSON
//    }
//
//    // hiển thị danh sách đơn hàng
//    @GetMapping("/view")
//    public String showOrderList(Model model,
//                                @RequestParam(defaultValue = "0") int page,
//                                @RequestParam(defaultValue = "5") int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<OrderDTO> ordersPage = orderService.getOrdersWithPagination(pageable);
//
//        model.addAttribute("ordersPage", ordersPage);
//        return "page/Admin/QuanLyDonHang";
//    }




}