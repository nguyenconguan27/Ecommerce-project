package com.example.ecommerceproject.security;

import com.example.ecommerceproject.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Integer id;
    private String username;
    private String fullName;
    private String number;
    private String address;
    private Integer cartId;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    @JsonIgnore
    private String password;

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoleList().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getFullName(), user.getNumber(), user.getAddress(), user.getCart().getId(), user.getEmail(), authorities, user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Integer getCartId() {
        return cartId;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
