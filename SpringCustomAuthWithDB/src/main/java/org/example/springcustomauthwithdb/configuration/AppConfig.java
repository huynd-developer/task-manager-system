package org.example.springcustomauthwithdb.configuration;

import org.example.springcustomauthwithdb.Service.UserDetailManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppConfig {
    protected final UserDetailManager userDetailManager;
    public AppConfig(UserDetailManager userDetailManager) {
        this.userDetailManager = userDetailManager;
    }
    @Bean
    public PasswordEncoder paswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).formLogin(Customizer.withDefaults())
                .rememberMe(r -> r.tokenValiditySeconds(3 * 24 * 60 * 60));
        return httpSecurity.build();
    }
}
