package com.example.ecommerceproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String address;
    private String email;
    private String fullName;
    private String number;
    @JsonIgnore
    private String password;
    private String username;
    private List<String> role;
}
