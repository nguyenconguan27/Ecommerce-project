package com.example.ecommerceproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receipt")
@Data
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "customer_number", nullable = false, length = 10)
    private String customerNumber;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
