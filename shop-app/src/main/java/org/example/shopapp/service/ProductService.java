package org.example.shopapp.service;

import org.example.shopapp.entity.Product;
import org.example.shopapp.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository, FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(imageFile);
            product.setImageUrl(imageUrl);
        }

        if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
            product.setImageUrl("/images/default-product.jpg");
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails, MultipartFile imageFile) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStockQuantity(productDetails.getStockQuantity());

        if (imageFile != null && !imageFile.isEmpty()) {
            String newImageUrl = fileStorageService.storeFile(imageFile);
            existingProduct.setImageUrl(newImageUrl);
        } else if (productDetails.getImageUrl() != null && !productDetails.getImageUrl().isEmpty()) {
            existingProduct.setImageUrl(productDetails.getImageUrl());
        }

        return productRepository.save(existingProduct);
    }

    public Product saveProduct(Product product) {
        if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
            product.setImageUrl("/images/default-product.jpg");
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

    public List<Product> getLowStockProducts(int maxResults) {
        try {
            return productRepository.findLowStockProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}