package com.example.ecommerceproject.converter;

import com.example.ecommerceproject.dto.ReviewDTO;
import com.example.ecommerceproject.entity.Product;
import com.example.ecommerceproject.entity.Review;
import com.example.ecommerceproject.entity.User;
import com.example.ecommerceproject.repository.ProductRepository;
import com.example.ecommerceproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class ReviewConverter {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final UserRepository userRepository;

    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
        reviewDTO.setProductId(review.getProduct().getId());
        reviewDTO.setUserId(review.getUser().getId());
        return reviewDTO;
    }
    public Review toEntity(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        User user = userRepository.findById(reviewDTO.getUserId()).get();
        Product product = productRepository.findById(reviewDTO.getProductId()).get();
        review.setProduct(product);
        review.setUser(user);
        return review;
    }
}
