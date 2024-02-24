package com.example.ecommerceproject.service.impl;


import com.example.ecommerceproject.converter.ProductConverter;
import com.example.ecommerceproject.dto.ProductDTO;
import com.example.ecommerceproject.entity.Product;
import com.example.ecommerceproject.exception.DataNotFoundException;
import com.example.ecommerceproject.repository.ProductRepository;
import com.example.ecommerceproject.payload.response.ProductListResponse;
import com.example.ecommerceproject.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductConverter productConverter;

    @Override
    public ProductDTO getOne(Integer id) throws DataNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new DataNotFoundException("Product not found");
        }
        return productConverter.toDTO(productRepository.findById(id).get());
    }

    @Override
    @Transactional
    public ProductDTO updateOrAdd(ProductDTO productDTO) {
        Product product = productConverter.toEntity(productDTO);
        productRepository.save(product);
        return productDTO;
}

    @Override
    @Transactional
    public void delete(Integer id) throws Exception {
        if(!productRepository.existsById(id)) {
            throw new Exception("Delete product fail");
        }
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }

    @Override
    public ProductListResponse getByOptions(Integer pageNum, Integer pageSize, String sortField, String sortDir, String title, Integer categoryId, Integer minPrice, Integer maxPrice) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Product> productPage;
        if (categoryId == 0) {
            productPage = productRepository.findByNoneCate(minPrice, maxPrice, title, pageable);
        } else {
            productPage = productRepository.findByALlOptions(minPrice, maxPrice, categoryId, title, pageable);
        }
        List<Product> productList = productPage.getContent();
        List<ProductDTO> productDTOList = productList.stream().map(element -> productConverter.toDTO(element)).collect(Collectors.toList());
        return new ProductListResponse(productDTOList, productPage.getTotalPages());
    }

    @Override
    public Boolean existingProduct(Integer id) {
        return productRepository.existsById(id);
    }
}
