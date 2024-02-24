package com.example.ecommerceproject.converter;

import com.example.ecommerceproject.dto.ImageDTO;
import com.example.ecommerceproject.dto.ProductDTO;
import com.example.ecommerceproject.entity.Category;
import com.example.ecommerceproject.entity.Image;
import com.example.ecommerceproject.entity.Product;
import com.example.ecommerceproject.entity.Review;
import com.example.ecommerceproject.repository.CategoryRepository;
import com.example.ecommerceproject.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@AllArgsConstructor
public class ProductConverter {
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategoryId(product.getCategory().getId());
        double sum = 0;
        for(Review review : product.getReviewList()) {
            sum += review.getRate();
        }
        List<ImageDTO> imageList = productDTO.getImageList().stream().map(image -> new ImageDTO(image.getFileName())).collect(Collectors.toList());
        productDTO.setRate(sum / (product.getReviewList().isEmpty() ? 1 : product.getReviewList().size()));
        productDTO.setImageList(imageList);
        return productDTO;
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepository.findById(productDTO.getCategoryId()).get();
        product.setCategory(category);
        return product;
    }


}
