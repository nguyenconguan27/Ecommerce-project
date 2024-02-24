package com.example.ecommerceproject.service;

import com.example.ecommerceproject.dto.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService {
     UserDTO getOneById(Integer id);
}
