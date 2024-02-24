package com.example.ecommerceproject.service;

import com.example.ecommerceproject.dto.OrderDetailDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface OrderDetailService {
     List<OrderDetailDTO> getAll();
     List<OrderDetailDTO> getAllByUser(Integer id);
     OrderDetailDTO update(OrderDetailDTO orderDetailDTO);
     void delete(Integer id);
}
