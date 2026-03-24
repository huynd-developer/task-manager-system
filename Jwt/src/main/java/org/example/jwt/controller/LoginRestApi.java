package org.example.jwt.controller;

import org.example.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginRestApi {
    // Bean dùng để xác thục username và password
    @Autowired
    AuthenticationManager authenticationManager;
    // Service tạo và xử lý jwt
    @Autowired
    JwtService jwtService;
    /**
     * API Endpoint: Post - /poly/login
     * Nhận thông tin đăng nhập (username,password) từ body (JSON)
     * Xác thực người dùng bằng Authentication Manager
     * Nếu thành công sẽ tạo jwt token và trả lại client
     */
    @PostMapping("/poly/login")
    public Object login(@RequestBody Map<String,String> userInfo) {
        // Lấy username và password
        String username = userInfo.get("username");
        String password = userInfo.get("password");
        // Tạo đối tượng chứa thông tin xác thực
        var authInfo = new UsernamePasswordAuthenticationToken(username, password);
        // Thực hiện xác thực
        var authentication = authenticationManager.authenticate(authInfo);
        if (authentication.isAuthenticated()){
            // Lấy thông tin người dùng
            UserDetails user = (UserDetails) authentication.getPrincipal();
            // Tạo jwt
            String token = jwtService.createJwt(user,20*60);
            // Trả về token dưới dạng json
            return Map.of("token", token);
        }
        // Xác thực thất bại
        throw new UsernameNotFoundException("Invalid username or password");
    }
}
