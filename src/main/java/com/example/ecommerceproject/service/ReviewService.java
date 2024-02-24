package com.example.ecommerceproject.service;

import com.example.ecommerceproject.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface ReviewService {
     List<ReviewDTO> getAllByClothes(Integer clothesId);
     ReviewDTO add(Integer clothesId, ReviewDTO reviewDTo);
}
