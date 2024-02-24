package com.example.ecommerceproject.converter;

import com.example.ecommerceproject.dto.CartDTO;
import com.example.ecommerceproject.entity.Cart;
import com.example.ecommerceproject.entity.User;
import com.example.ecommerceproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class CartConverter {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final UserRepository userRepository;
    public CartDTO toDtO(Cart cart) {
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cartDTO.setUserId(cart.getUser().getId());
        return cartDTO;
    }

    public Cart toEntity(CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO, Cart.class);
        User user = userRepository.findById(cartDTO.getId()).get();
        cart.setUser(user);
        return cart;
    }
}
