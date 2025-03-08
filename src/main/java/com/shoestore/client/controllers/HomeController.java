package com.shoestore.client.controllers;

import com.shoestore.client.dto.request.ProductHomeDTO;
import com.shoestore.client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
//  @Autowired
//  private ProductService productService;
//
//  @GetMapping("/home")
//  public String home(Model model) {
//    // Fetch Top 10 Best Sellers, New Arrivals, and Trending Products
//    List<ProductHomeDTO> top10BestSellers = productService.getTop10BestSellers();
//    List<ProductHomeDTO> top10NewArrivals = productService.getTop10NewArrivals();
//    List<ProductHomeDTO> top10Trending = productService.getTop10Trending();
//    System.out.println(top10BestSellers);
//    // Add the data to the model to pass to the Thymeleaf template
//    model.addAttribute("top10BestSellers", top10BestSellers);
//    model.addAttribute("top10NewArrivals", top10NewArrivals);
//    model.addAttribute("top10Trending", top10Trending);
//
//    return "page/Customer/Home"; // Return the home page view
//  }
}
