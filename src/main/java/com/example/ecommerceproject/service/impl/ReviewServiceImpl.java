package com.example.ecommerceproject.service.impl;

import com.example.ecommerceproject.dto.ReviewDTO;
import com.example.ecommerceproject.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public ArrayList<ReviewDTO> getAllByClothes(Integer clothesId) {
        return null;
    }

    @Override
    public ReviewDTO add(Integer clothesId, ReviewDTO reviewDTo) {
        return null;
    }
}
