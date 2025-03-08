package com.shoestore.client.controllers;


/*
    @author: Đào Thanh Phú
    Date: 11/26/2024
    Time: 8:08 PM
    ProjectName: Client
*/


import com.shoestore.client.dto.request.ReviewDTO;
import com.shoestore.client.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/product/review")
    public String listReviewForAdmin(Model model){
        List<ReviewDTO> reviews= reviewService.getAllReview();
        model.addAttribute("reviews",reviews);
        System.out.println(reviews);
        return "page/Admin/QuanLyBaiDanhGia";
    }
}
