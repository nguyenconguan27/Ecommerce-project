package com.example.ecommerceproject.service.impl;

import com.example.ecommerceproject.dto.UserDTO;
import com.example.ecommerceproject.entity.Cart;
import com.example.ecommerceproject.entity.Role;
import com.example.ecommerceproject.entity.User;
import com.example.ecommerceproject.exception.DataConfictException;
import com.example.ecommerceproject.exception.LoginFailException;
import com.example.ecommerceproject.payload.request.LoginRequest;
import com.example.ecommerceproject.payload.request.RegisterRequest;
import com.example.ecommerceproject.payload.response.LoginResponse;
import com.example.ecommerceproject.payload.response.RefreshTokenResponse;
import com.example.ecommerceproject.repository.CartRepository;
import com.example.ecommerceproject.repository.RoleRepository;
import com.example.ecommerceproject.repository.UserRepository;
import com.example.ecommerceproject.security.CustomUserDetails;
import com.example.ecommerceproject.security.JwtProvider;
import com.example.ecommerceproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws LoginFailException {
        Map<String, String> errorResponse = new HashMap<>();
        if(!userRepository.existsByUsername(loginRequest.getUsername())) {
            errorResponse.put("username", "Username is not exist");
        }
        else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
            if (authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String accessToken = jwtProvider.generateToken(authentication.getName());
                String refreshToken = jwtProvider.generateRefreshToken(userDetails.getId() + "");
                return new LoginResponse(
                        new UserDTO(
                                userDetails.getId(),
                                userDetails.getAddress(),
                                userDetails.getEmail(),
                                userDetails.getFullName(),
                                userDetails.getNumber(),
                                userDetails.getPassword(),
                                userDetails.getUsername(),
                                userDetails.getAuthorities().stream().map(
                                        role -> role.getAuthority()
                                ).collect(Collectors.toList())

                        ),
                        accessToken,
                        refreshToken
                );
            }
            else {
                errorResponse.put("password", "Wrong password");
            }
        }
        throw new LoginFailException("Login fail", errorResponse);
    }


    @Override
    public LoginRequest register(RegisterRequest registerRequest) throws DataConfictException {
        Map<String, String> errorResponse = new HashMap<>();
        if (userRepository.existsByUsername(registerRequest.getUsername())
                || userRepository.existsByEmail(registerRequest.getEmail())
                || userRepository.existsByNumber(registerRequest.getNumber())) {
            if (userRepository.existsByUsername(registerRequest.getUsername())) {
                errorResponse.put("username", "Username is already exist");
            }
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                errorResponse.put("email", "Email is already exist");
            }
            if (userRepository.existsByNumber(registerRequest.getNumber())) {
                errorResponse.put("number", "Number is already exist");
            }
        }
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            errorResponse.put("confirmPassword", "confirm password is not match with password");
        }
        else {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setAddress(registerRequest.getAddress());
            user.setFullName(registerRequest.getFullName());
            user.setNumber(registerRequest.getNumber());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode((registerRequest.getPassword())));
            Role role = roleRepository.findByRoleName("USER").get();
            user.setRoleList(Collections.singletonList(role));
            Cart cart = new Cart();
            userRepository.save(user);
            cart.setUser(user);
            cartRepository.save(cart);
            return new LoginRequest(user.getUsername(), user.getPassword());
        }
        throw new DataConfictException("Register is fail", errorResponse);
    }

    @Override
    public RefreshTokenResponse refreshToken(String refreshToken, String username) throws LoginFailException {
        if (JwtProvider.validateRefreshToken(refreshToken)) {
            String accessToken = jwtProvider.generateToken(username);
            return new RefreshTokenResponse(accessToken, refreshToken);
        }
        throw new LoginFailException("Refresh token was expired", null);
    }
}
