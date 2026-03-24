package org.example.springoauthwithgoogle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource,PasswordEncoder passwordEncoder){
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
    public SecurityFilterChain spriSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth->{
                    auth.requestMatchers("/poly/**").authenticated();
                    auth.anyRequest().permitAll();
                })
                // cấu hình dăng nhập bằng tài khoản trong db
                .formLogin(config->{
                    config.loginPage("/auth/login");
                    config.loginProcessingUrl("/auth/check");
                    config.defaultSuccessUrl("/",true);
                    config.failureUrl("/auth/login");
                    config.usernameParameter("username");
                    config.passwordParameter("password");
                })
                // cấu hình đăng nhập bầng tài khoản google
                .oauth2Login(config->{
                    config.loginPage("/auth/login");
                    config.defaultSuccessUrl("/",true);
                    config.failureUrl("/auth/login");
                    config.userInfoEndpoint(userInfo->{
                        userInfo.userService(oauth2UserService());
                    });
                })
                .rememberMe(config->{
                    config.tokenValiditySeconds(3*24*60*60);
                    config.rememberMeParameter("remember-me");
                    config.rememberMeCookieName("remember-me");
                })
                .logout(config->{
                    config.logoutUrl("/auth/logout");
                    config.logoutSuccessUrl("/");
                    config.clearAuthentication(true);
                    config.invalidateHttpSession(true);
                    config.deleteCookies("remember-me", "JSESSIONID");
                });
        return httpSecurity.build();
    }
    // Phương thức xử lý thông tin sau khi đăng nhập
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(){
        return userRequest -> {
         OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
         OAuth2User oAuth2User = delegate.loadUser(userRequest);
         String email = oAuth2User.getAttribute("email");
         String name = oAuth2User.getAttribute("name");
         // Ghi vào DB
         return oAuth2User;
        };
    }
}
