package com.shoestore.client.service.impl;


/*
    @author: Đào Thanh Phú
    Date: 11/26/2024
    Time: 8:04 PM
    ProjectName: Client
*/


import com.shoestore.client.dto.request.ReviewDTO;
import com.shoestore.client.dto.response.ReviewResponseDTO;
import com.shoestore.client.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<ReviewDTO> getAllReview() {
        String apiUrl="http://localhost:8080/reviews";
        ResponseEntity<ReviewResponseDTO> response= restTemplate.exchange(
                apiUrl, HttpMethod.GET,null, ReviewResponseDTO.class
        );
        System.out.println("Response Body: " + response.getBody());
        return response.getBody().getReviewDTOs();
    }
}
