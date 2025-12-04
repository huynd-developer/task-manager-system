package com.example.SpringSecuritywithSQL.Service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//component, bean, Respository
@Service("auth")
public class AuthService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    //Lấy ra username được quản lý bởi Spring Security
    public String getUsername() {
        return getAuthentication().getName();
    }

    //Giả sử là string s = "ROLE_ADMIN" nên lấy ra chuỗi ADMIN
    //Lấy ra bộ vai trò được quản lý bởi Spring Security
    public List<String> getRoles() {
        return this.getAuthentication().getAuthorities().stream().map(
                authority -> authority.getAuthority().substring(5)).collect(Collectors.toList());
    }

    //Kiểm tra xem có quyền nào được gán hay không
    public boolean hasAnyRole(String... roleToCheck) {
        var grantedRoles = this.getRoles();
        return Stream.of(roleToCheck).anyMatch(grantedRoles::contains);
    }
}