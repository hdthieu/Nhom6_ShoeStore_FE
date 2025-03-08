package com.shoestore.client.service;


/*
    @author: Đào Thanh Phú
    Date: 11/26/2024
    Time: 8:03 PM
    ProjectName: Client
*/

import com.shoestore.client.dto.request.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    public List<ReviewDTO> getAllReview();
}
