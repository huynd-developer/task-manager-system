package com.example.SpringSecuritywithSQL.Config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableAutoConfiguration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, DataSource dataSource) {
       return new JdbcUserDetailsManager(dataSource);
    } //giống dữ liệu tên bảng và tên thực thể
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Vô hiệu CORS và CSRF
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/poly/url1").authenticated();
            authorizeRequests.requestMatchers("/poly/url2").hasRole("USER");
            authorizeRequests.requestMatchers("/poly/url3").hasRole("ADMIN");
            authorizeRequests.requestMatchers("/poly/url4").hasAnyRole("ADMIN", "USER");
            authorizeRequests.anyRequest().permitAll();
        });

        // Cú pháp chuẩn hơn cho formLogin để cho phép tất cả
        http.formLogin(form -> form.permitAll());

        http.rememberMe(config -> {
            config.tokenValiditySeconds(5 * 24 * 60 * 60); // 5 ngày
        });

        return http.build();
    }
//sử dụng cấu trúc của spring quy định
}
