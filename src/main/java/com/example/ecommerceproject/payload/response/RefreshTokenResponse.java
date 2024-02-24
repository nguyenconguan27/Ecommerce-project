package com.example.ecommerceproject.payload.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RefreshTokenResponse {
    String accessToken;
    String refreshToken;
}
