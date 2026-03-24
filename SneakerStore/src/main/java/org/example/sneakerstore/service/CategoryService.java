package org.example.sneakerstore.service;
import org.example.sneakerstore.entity.Category;
import org.example.sneakerstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public boolean categoryExists(String name) {
        return categoryRepository.existsByName(name);
    }

    public List<Category> getActiveCategories() {
        // Trong trường hợp bạn muốn lọc các danh mục đang hoạt động
        return categoryRepository.findAll();
    }

    public long getTotalCategories() {
        return categoryRepository.count();
    }
}
