package org.example.th04383.controller;

import org.example.th04383.request.ChamCongRequest;
import org.example.th04383.response.ChamCongResponse;
import org.example.th04383.service.ChamCongService;
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
@CrossOrigin(origins = "http://localhost:5175")
@RequestMapping("/cham-cong")
public class ChamCongController {
    @Autowired
    ChamCongService chamCongService;
    @GetMapping("/hien-thi")
    public List<ChamCongResponse> getAll(){
        return chamCongService.getAll();
    }
    @GetMapping("/phan-trang")
    public List<ChamCongResponse> phanTrang(@RequestParam Integer page){
        int pageSize = 5;
        return chamCongService.phanTrang(page, pageSize);
    }
    @PostMapping("/add")
    public void add(@RequestBody ChamCongRequest chamCongRequest){
        chamCongService.add(chamCongRequest);
    }
    @PutMapping("/update")
    public void update(@RequestBody ChamCongRequest chamCongRequest){
        chamCongService.update(chamCongRequest);
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam Integer id){
        chamCongService.delete(id);
    }
}
