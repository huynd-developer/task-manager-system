package org.example.j6backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder pe) {
        String password = pe.encode("123");
        UserDetails user1 = User.withUsername("user@gmail.com").password(password).roles("USER").build();
        UserDetails user2 = User.withUsername("admin@gmail.com").password(password).roles("ADMIN").build();
        UserDetails user3 = User.withUsername("both@gmail.com").password(password).roles("USER", "ADMIN").build(); // Sửa: roles("USER", "ADMIN")
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfig corsConfig,
                                                   CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                // Cấu hình CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                // Tắt CSRF cho API (khuyến nghị cho REST API)
                .csrf(AbstractHttpConfigurer::disable)

                // Cấu hình authorization - QUAN TRỌNG: anyRequest() PHẢI Ở CUỐI
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers("/poly/**").permitAll()// Bổ sung url cho chat topic
                        // Cấu hình các route cụ thể TRƯỚC
                        .requestMatchers("/students", "/students/**").permitAll() // SỬA: thêm dấu "/"

                        // Cấu hình các route khác nếu cần
                        // .requestMatchers("/admin/**").hasRole("ADMIN")
                        // .requestMatchers("/user/**").hasRole("USER")

                        // DÒNG CUỐI CÙNG: cấu hình tất cả request còn lại
                        .anyRequest().permitAll() // hoặc .authenticated() nếu muốn yêu cầu đăng nhập
                );

        return http.build();
    }
}