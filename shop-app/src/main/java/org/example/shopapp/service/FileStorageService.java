package org.example.shopapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        try {
            System.out.println("=== FILE UPLOAD DEBUG ===");
            System.out.println("Original filename: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize() + " bytes");
            System.out.println("Upload dir from properties: " + uploadDir);

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            System.out.println("Absolute upload path: " + uploadPath.toString());

            if (!Files.exists(uploadPath)) {
                System.out.println("Creating directory: " + uploadPath);
                Files.createDirectories(uploadPath);
                System.out.println("Directory created successfully!");
            } else {
                System.out.println("Directory already exists");
            }

            System.out.println("Is writable: " + Files.isWritable(uploadPath));

            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String fileName = System.currentTimeMillis() + "_" +
                    (originalFileName != null ? originalFileName.replace(" ", "_") : "file");
            System.out.println("Generated filename: " + fileName);

            Path targetLocation = uploadPath.resolve(fileName);
            System.out.println("Target location: " + targetLocation);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/uploads/" + fileName;
            System.out.println("File saved! Access URL: " + fileUrl);
            System.out.println("=== END DEBUG ===");

            return fileUrl;

        } catch (IOException e) {
            System.err.println("ERROR saving file: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Không thể lưu file: " + e.getMessage());
        }
    }
}