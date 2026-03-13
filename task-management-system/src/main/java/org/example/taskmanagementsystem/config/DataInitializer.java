package org.example.taskmanagementsystem.config;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.entity.enums.UserRole;
import org.example.taskmanagementsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Quản trị viên hệ thống");
            admin.setRole(UserRole.ROLE_ADMIN);

            userRepository.save(admin);
            System.out.println("----------------------------------------------");
            System.out.println(">>> ĐÃ KHỞI TẠO TÀI KHOẢN ADMIN: admin / admin123");
            System.out.println("----------------------------------------------");
        }
    }
}