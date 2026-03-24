package com.example.SpringCostumLogin.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Cần import cái này
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableAutoConfiguration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // ... (code này không thay đổi)
        String pass = passwordEncoder.encode("123456");
        UserDetails user1 = User.withUsername("dao@gmail.com").password(pass).roles("USER").build();
        UserDetails user2 = User.withUsername("admin@gmail.com").password(pass).roles("ADMIN").build();
        UserDetails user3 = User.withUsername("both@gmail.com").password(pass).roles("BOTH").build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // ---- ĐOẠN CODE BẠN YÊU CẦU TỪ ẢNH ----
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable); // Tắt luôn CORS

        http.authorizeHttpRequests(auth -> auth
                // DÒNG NÀY SẼ "MỞ TOANG" BẢO MẬT CỦA BẠN
                .anyRequest().permitAll()
        );
        // ---- HẾT ĐOẠN CODE TRONG ẢNH ----


        // VÀ BÂY GIỜ... CÁC CẤU HÌNH NÀY SẼ TRỞ NÊN VÔ NGHĨA

        http.formLogin(config -> {
            config.loginPage("/auth/login"); // Sẽ không bao giờ được gọi
            config.loginProcessingUrl("/auth/check");
            config.defaultSuccessUrl("/auth/success", true); // Cũng vô nghĩa
            config.failureForwardUrl("/auth/failure");
            config.usernameParameter("username");
            config.passwordParameter("password");
        });

        // ... (logout và rememberMe cũng vô nghĩa nốt)
        http.rememberMe(cfg -> {
            cfg.rememberMeParameter("remember-me");
            cfg.tokenValiditySeconds(3 * 24 * 60 * 60);
        });


        http.logout(cfg -> {
            cfg.logoutUrl("/auth/logout");
            cfg.logoutSuccessUrl("/auth/exit");
            cfg.clearAuthentication(true);
            cfg.invalidateHttpSession(true);
            cfg.deleteCookies("JSESSIONID");
        });

        return http.build();
    }
}