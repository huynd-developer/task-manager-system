package org.example.huynd_th04383.Service;

import jakarta.validation.Valid;
import org.example.huynd_th04383.Entity.Sach;
import org.example.huynd_th04383.Repository.SachRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SachService {
    private SachRepository sachRepository;
    public SachService(SachRepository sachRepository) {
        this.sachRepository = sachRepository;
    }
    public List<Sach> getAllSach(){
        return sachRepository.findAll();
    }
    public void saveSach(@Valid Sach sach){
        sachRepository.save(sach);
    }
    public void updateSach(@Valid Sach sach){
        sachRepository.save(sach);
    }
    public void deleteSach(Integer id){
        sachRepository.deleteById(id);
    }
    public Sach getSachById(Integer id){
        return sachRepository.findById(id).get();
    }
    public Page<Sach> phanTrang(int page, int size){
        return sachRepository.findAll(PageRequest.of(page, size));
    }
    public Page<Sach> search(String keyword,int page, int size){
        return sachRepository.findByTenSachContaining(keyword,PageRequest.of(page, size));
    }
}
