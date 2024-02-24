package com.example.ecommerceproject.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String address;
    @Email(message = "Email is invalidate")
    private String email;
    @NotBlank(message = "fullName is mandatory")
    private String fullName;
    @NotBlank(message = "number is mandatory")
    private String number;
    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "password must have mort than 8 characters")
    private String password;
    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "confirmPassword is mandatory")
    @Size(min = 8, message = "password must have mort than 8 characters")
    private String confirmPassword;
}
