package com.example.demo.Controller;

import com.example.demo.Request.DonHangRequest;
import com.example.demo.Response.DonHangResponse;
import com.example.demo.Service.DonHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/don-hang")
public class DonHangController {
    @Autowired
    DonHangService donHangService;

    @GetMapping("/hien-thi")
    public List<DonHangResponse> hienThi(){
        return donHangService.getAll();
    }

    @GetMapping("/phan-trang")
    public List<DonHangResponse> phanTrang(@RequestParam Integer page){
        int pageSize = 3;
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
