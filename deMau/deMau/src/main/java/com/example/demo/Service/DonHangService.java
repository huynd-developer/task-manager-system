package com.example.demo.Service;

import com.example.demo.Entity.DonHang;
import com.example.demo.Exception.ApiException;
import com.example.demo.Repository.DonHangRepository;
import com.example.demo.Request.DonHangRequest;
import com.example.demo.Response.DonHangResponse;
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

    public List<DonHangResponse> phanTrang(Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return donHangRepository.findAll(pageable).getContent().stream().map(DonHangResponse::new).toList();
    }

    public void add(DonHangRequest donHangRequest){
        DonHang donHang = new DonHang();
        BeanUtils.copyProperties(donHangRequest,donHang);
        donHangRepository.save(donHang);
    }

    public void update(DonHangRequest donHangRequest){
        DonHang donHang = new DonHang();
        BeanUtils.copyProperties(donHangRequest,donHang);
        donHangRepository.save(donHang);
    }

    public void delete(Integer id){
        donHangRepository.findById(id).orElseThrow(() -> new ApiException("Khong tim thay ID","ID_NOT_FOUND"));
        donHangRepository.deleteById(id);
    }

}
