package org.example.th04383.response;

import jakarta.persistence.Column;
import lombok.Data;
import org.example.th04383.entity.ChamCong;

@Data
public class ChamCongResponse {
    private Integer id;
    private String ngayCham;
    private Float soGioLam;
    private Float phat;
    private String tenNhanVien;
    private String phongBan;
    public ChamCongResponse(ChamCong chamCong){
        this.id = chamCong.getId();
        this.ngayCham = chamCong.getNgayCham();
        this.soGioLam = chamCong.getSoGioLam();
        this.phat = chamCong.getPhat();
        this.tenNhanVien = chamCong.getNhanVien().getTenNhanVien();
        this.phongBan = chamCong.getNhanVien().getPhongBan();
    }
}
