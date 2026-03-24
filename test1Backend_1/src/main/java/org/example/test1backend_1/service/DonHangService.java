package org.example.test1backend_1.service;

import org.example.test1backend_1.entity.DonHang;
import org.example.test1backend_1.exception.ApiException;
import org.example.test1backend_1.repository.DonHangRepository;
import org.example.test1backend_1.request.DonHangRequest;
import org.example.test1backend_1.response.DonHangResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangService {
    @Autowired
    DonHangRepository donHangRepository;
    public List<DonHangResponse> getAll(){
        return donHangRepository.findAll().stream().map(DonHangResponse::new).toList();
    }
    public List<DonHangResponse> phanTrang(Integer page,Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return donHangRepository.findAll(pageable).getContent().stream().map(DonHangResponse::new).toList();
    }
    public void add(DonHangRequest donHangRequest){
        DonHang donHang = new DonHang();
        BeanUtils.copyProperties(donHangRequest, donHang);
        donHangRepository.save(donHang);
    }
    public void update(DonHangRequest donHangRequest){
        DonHang donHang = donHangRepository.findById(donHangRequest.getId()).orElseThrow(()->new ApiException("ID_NOT_FOUND","Không tìm thấy đơn hàng"));
        BeanUtils.copyProperties(donHangRequest, donHang);
        donHangRepository.save(donHang);
    }
    public void delete(Integer id){
        donHangRepository.findById(id).orElseThrow(()->new ApiException("ID_NOT_FOUND","Không tìm thấy ID"));
        donHangRepository.deleteById(id);
    }
}
