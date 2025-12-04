package org.example.huynd_th04383.Controller;

import jakarta.validation.Valid;
import org.example.huynd_th04383.Entity.Sach;
import org.example.huynd_th04383.Entity.TacGia;
import org.example.huynd_th04383.Service.SachService;
import org.example.huynd_th04383.Service.TacGiaService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SachController {
    private SachService  sachService;
    private TacGiaService tacGiaService;
    public SachController(SachService sachService,TacGiaService tacGiaService) {
        this.sachService = sachService;
        this.tacGiaService = tacGiaService;
    }
    @GetMapping("/sach")
    public String sach(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String keyword){
        Page<Sach> sach;
        if(keyword!=null && !keyword.isEmpty()){
            sach = sachService.search(keyword,page,size);
            model.addAttribute("keyword",keyword);
        }else {
            sach = sachService.phanTrang(page,size);
        }
        model.addAttribute("sach",sach.getContent());
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",sach.getTotalPages());
        return "views/sach";
    }
    @GetMapping("/sach/formAdd")
    public String formAdd(Model model){
        model.addAttribute("sach",new Sach());
        model.addAttribute("tacGia",tacGiaService.findAll());
        return "views/form_add";
    }
    @PostMapping("/sach/addSach")
    public String addSach(@Valid @ModelAttribute("sach") Sach sach, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("tacGia",tacGiaService.findAll());
            return "views/form_add";
        }
        sachService.saveSach(sach);
        return "redirect:/sach";
    }
    @GetMapping("/sach/formUpdate/{id}")
    public String formUpdate(@PathVariable Integer id, Model model){
        Sach sach = sachService.getSachById(id);
        model.addAttribute("sach",sach);
        model.addAttribute("tacGia",tacGiaService.findAll());
        return "views/form_update";
    }
    @PostMapping("/sach/updateSach")
    public String updateSach(@Valid @ModelAttribute("sach") Sach sach, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("tacGia",tacGiaService.findAll());
            return "views/form_update";
        }
        sachService.updateSach(sach);
        return "redirect:/sach";
    }
    @GetMapping("/sach/deleteSach/{id}")
    public String deleteSach(@PathVariable Integer id){
        sachService.deleteSach(id);
        return "redirect:/sach";
    }
}
