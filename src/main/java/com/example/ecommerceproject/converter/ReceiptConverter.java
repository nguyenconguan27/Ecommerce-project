package com.example.ecommerceproject.converter;

import com.example.ecommerceproject.dto.ReceiptDTO;
import com.example.ecommerceproject.entity.Receipt;
import com.example.ecommerceproject.entity.User;
import com.example.ecommerceproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class ReceiptConverter {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final UserRepository userRepository;

    public ReceiptDTO toDTO(Receipt receipt) {
        ReceiptDTO receiptDTO = modelMapper.map(receipt, ReceiptDTO.class);
        receiptDTO.setUserId(receipt.getUser().getId());
        return receiptDTO;
    }

    public Receipt toEntity(ReceiptDTO receiptDTO) {
        Receipt receipt = modelMapper.map(receiptDTO, Receipt.class);
        User user = userRepository.findById(receiptDTO.getUserId()).get();
        return receipt;
    }
}
