package org.example.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.nio.charset.StandardCharsets; // Thêm import này
import java.util.Date;
import java.util.HashMap; // Thay thế Map.of để tương thích
import java.util.Map;

@Service
public class JwtService {
    // SInh key dạng mã hóa
    private Key getSigningKey() {
        // Gỉa sử key của ứng dụng
        // SỬA: Tăng độ dài key lên ít nhất 32 ký tự (256 bits)
        String secretKey = "123456789.123456789.123456789.123"; // 32 ký tự

        // Chuyển sang byte[]
        byte[] encodedKey = secretKey.getBytes(StandardCharsets.UTF_8);

        // Kiểm tra độ dài key
        if (encodedKey.length < 32) {
            // Nếu key quá ngắn, tạo key đủ dài
            byte[] paddedKey = new byte[32];
            System.arraycopy(encodedKey, 0, paddedKey, 0, Math.min(encodedKey.length, 32));
            encodedKey = paddedKey;
        }

        return Keys.hmacShaKeyFor(encodedKey); // Thuật toán HMAC
    }

    // Tạo Jwt
    public String createJwt(UserDetails user, int expiredSeconds) {
        // Lấy thời gin hiện tại của hệ thống
        long now = System.currentTimeMillis();

        // Tạo map claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "Poly");

        // Tạo key
        return Jwts.builder()
                // Gán claim
                .setClaims(claims)
                // Tiêu đề
                .setSubject(user.getUsername())
                // Ngày phát hành token
                .setIssuedAt(new Date(now))
                // Ngày hết hạn
                .setExpiration(new Date(now + 1000L * expiredSeconds))
                // Ký token - SỬA: Dùng HS256 thay vì ES256 vì key là HMAC
                .signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
                // Nén và tạo key
                .compact();
    }

    // Xử lý body
    public Claims getBody(String token) {
        return Jwts.parserBuilder()
                // cung cấp khóa bí mật
                .setSigningKey(this.getSigningKey())
                // Tạo đối tượng parser
                .build()
                // phân tích Jwt
                .parseClaimsJws(token)
                .getBody();
    }

    // Kiểm tra tính hợp lệ của token sinh ra
    public boolean validate(Claims claims){
        // So sánh thời gian hết hạn của token với thời gian
        return claims.getExpiration().after(new Date());
    }
}