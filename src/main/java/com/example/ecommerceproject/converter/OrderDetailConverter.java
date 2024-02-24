package com.example.ecommerceproject.converter;

import com.example.ecommerceproject.dto.OrderDetailDTO;
import com.example.ecommerceproject.entity.Cart;
import com.example.ecommerceproject.entity.Product;
import com.example.ecommerceproject.entity.OrderDetail;
import com.example.ecommerceproject.entity.Receipt;
import com.example.ecommerceproject.repository.CartRepository;
import com.example.ecommerceproject.repository.ProductRepository;
import com.example.ecommerceproject.repository.ReceiptRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class OrderDetailConverter {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ReceiptRepository receiptRepository;

    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
        orderDetailDTO.setCartId(orderDetail.getCart().getId());
        orderDetailDTO.setProductId(orderDetail.getProduct().getId());
        if(orderDetail.getReceipt() != null) {
            orderDetailDTO.setReceiptId(orderDetail.getReceipt().getId());
        }
        return orderDetailDTO;
    }

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
        Cart cart = cartRepository.findById(orderDetailDTO.getCartId()).get();
        Product product = productRepository.findById(orderDetailDTO.getProductId()).get();
        Receipt receipt = receiptRepository.findById(orderDetailDTO.getReceiptId()).get();
        orderDetail.setCart(cart);
        orderDetail.setProduct(product);
        orderDetail.setReceipt(receipt);
        return orderDetail;
    }
}
