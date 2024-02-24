package com.example.ecommerceproject.service.impl;

import com.example.ecommerceproject.dto.OrderDetailDTO;
import com.example.ecommerceproject.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Override
    public ArrayList<OrderDetailDTO> getAll() {
        return null;
    }

    @Override
    public List<OrderDetailDTO> getAllByUser(Integer id) {
        return null;
    }

    @Override
    public OrderDetailDTO update(OrderDetailDTO orderDetailDTO) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
