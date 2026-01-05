package org.example.test1backend_1.controller;

import jakarta.validation.Valid;
import org.example.test1backend_1.request.DonHangRequest;
import org.example.test1backend_1.response.DonHangResponse;
import org.example.test1backend_1.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/don-hang")
public class DonHangController {
    @Autowired
    DonHangService donHangService;
    @GetMapping("/hien-thi")
    public List<DonHangResponse> getAll(){
        return donHangService.getAll();
    }
    @GetMapping("/phan-trang")
    public List<DonHangResponse> phanTrang(@RequestParam Integer page){
        int pageSize = 5;
        return donHangService.phanTrang(page,pageSize);
    }
    @PostMapping("/add")
    public void add(@RequestBody @Valid DonHangRequest donHangRequest){
        donHangService.add(donHangRequest);
    }
    @PutMapping("/update")
    public void update(@RequestBody @Valid DonHangRequest donHangRequest){
        donHangService.update(donHangRequest);
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam Integer id){
        donHangService.delete(id);
    }
}
