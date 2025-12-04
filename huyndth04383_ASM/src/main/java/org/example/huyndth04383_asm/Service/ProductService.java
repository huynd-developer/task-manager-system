package org.example.huyndth04383_asm.Service;
import org.example.huyndth04383_asm.Entity.Product;
import org.example.huyndth04383_asm.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService {
    @Autowired private ProductRepository repo;

    public Page<Product> page(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p);
    }
    public Product getById(Integer id){ return repo.findById(id).orElse(null); }
    public Product save(Product p){ return repo.save(p); }
    public void delete(Integer id){ repo.deleteById(id); }
}

