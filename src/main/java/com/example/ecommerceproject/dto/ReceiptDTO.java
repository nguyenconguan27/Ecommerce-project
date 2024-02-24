package com.example.ecommerceproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {
    private Integer id;
    private String address;
    private String customerName;
    private String CustomerNumber;
    private Date orderDate;
    private Integer status;
    private Integer userId;
}
