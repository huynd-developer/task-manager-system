package org.example.sneakerstoremanagement.service;
import org.example.sneakerstoremanagement.entity.Sneaker;
import org.example.sneakerstoremanagement.repository.SneakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SneakerService {

    @Autowired
    private SneakerRepository sneakerRepo;

    public List<Sneaker> getAll() {
        return sneakerRepo.findAll();
    }

    public Optional<Sneaker> getById(Integer id) {
        return sneakerRepo.findById(id);
    }

    public Sneaker save(Sneaker sneaker) {
        return sneakerRepo.save(sneaker);
    }

    public void deleteById(Integer id) {
        sneakerRepo.deleteById(id);
    }

    public List<Sneaker> searchByName(String name) {
        return sneakerRepo.findByNameContainingIgnoreCase(name);
    }
}
