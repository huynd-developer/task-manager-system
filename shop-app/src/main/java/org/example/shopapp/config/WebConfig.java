package org.example.shopapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();

        System.out.println("=== WEB CONFIG DEBUG ===");
        System.out.println("🌐 Current working directory: " + System.getProperty("user.dir"));
        System.out.println("🌐 Upload directory path: " + uploadDir.toString());
        System.out.println("🌐 Directory exists: " + java.nio.file.Files.exists(uploadDir));
        System.out.println("=== END DEBUG ===");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir.toString() + "/")
                .setCachePeriod(0);
    }
}