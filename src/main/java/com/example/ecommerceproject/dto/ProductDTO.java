package com.example.ecommerceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String brand;
    private Integer price;
    private String title;
    private Integer quantity;
    private Integer sold;
    private Integer categoryId;
    private List<ImageDTO> imageList;
    private Double rate;
}
