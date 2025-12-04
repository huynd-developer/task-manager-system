package org.example.huynd_th04383.Service;

import org.example.huynd_th04383.Entity.TacGia;
import org.example.huynd_th04383.Repository.TacGiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacGiaService {
    private TacGiaRepository  tacGiaRepository;
    public TacGiaService(TacGiaRepository tacGiaRepository) {
        this.tacGiaRepository = tacGiaRepository;
    }
    public List<TacGia>  findAll(){
        return tacGiaRepository.findAll();
    }
}
