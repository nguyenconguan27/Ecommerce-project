package com.example.ecommerceproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role {
    @Id
    private Integer id;

    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;
}
