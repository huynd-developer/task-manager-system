package org.example.test1backend.Service;

import org.example.test1backend.Exception.ApiException;
import org.example.test1backend.entity.DonHang;
import org.example.test1backend.repository.DonHangRepository;
import org.example.test1backend.request.DonHangRequest;
import org.example.test1backend.response.DonHangResponse;
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
        return  donHangRepository.findAll().stream().map(DonHangResponse::new).toList();
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
        DonHang donHang = donHangRepository.findById(donHangRequest.getId())
                .orElseThrow(() ->
                        new ApiException("ID_NOT_FOUND", "Không tìm thấy đơn hàng"));

        BeanUtils.copyProperties(donHangRequest, donHang);
        donHangRepository.save(donHang);
    }
    public void delete(Integer id){
        donHangRepository.findById(id).orElseThrow(()-> new ApiException("Không tìm thấy ID","ID_NOT_FOUND"));
        donHangRepository.deleteById(id);
    }
}
