package com.example.ecommerceproject.service;

import com.example.ecommerceproject.dto.ReceiptDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface ReceiptService {
     List<ReceiptDTO> getAll();
     void delete(Integer id);
}
