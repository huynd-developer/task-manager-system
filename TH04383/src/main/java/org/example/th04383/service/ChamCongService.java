package org.example.th04383.service;

import org.example.th04383.entity.ChamCong;
import org.example.th04383.exception.ApiException;
import org.example.th04383.repository.ChamCongRepository;
import org.example.th04383.request.ChamCongRequest;
import org.example.th04383.response.ChamCongResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamCongService {
    @Autowired
    ChamCongRepository chamCongRepository;

    public List<ChamCongResponse> getAll() {
        return chamCongRepository.findAll().stream().map(ChamCongResponse::new).toList();
    }

    public List<ChamCongResponse> phanTrang(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return chamCongRepository.findAll(pageable).getContent().stream().map(ChamCongResponse::new).toList();
    }

    public void add(ChamCongRequest chamCongRequest) {
        ChamCong chamCong = new ChamCong();
        BeanUtils.copyProperties(chamCongRequest, chamCong);
        chamCongRepository.save(chamCong);
    }

    public void update(ChamCongRequest chamCongRequest) {
        ChamCong chamCong = chamCongRepository.findById(chamCongRequest.getId()).orElseThrow(() -> new ApiException("ID_NOT_FOUND", "Khog tim thay cham cong"));
        BeanUtils.copyProperties(chamCongRequest, chamCong);
        chamCongRepository.save(chamCong);
    }

    public void delete(Integer id) {
        chamCongRepository.findById(id).orElseThrow(() -> new ApiException("ID_NOT_FOUND", "Khog tim thay id"));
        chamCongRepository.deleteById(id);
    }
}
