package org.example.test1backend.response;

import lombok.Data;
import org.example.test1backend.entity.DonHang;

@Data
public class DonHangResponse {
    private Integer id;
    private String maDonHang;
    private String ngayDat;
    private Float tongTien;
    private String tenKhachHang;
    private String diaChi;

    public DonHangResponse(DonHang donHang) {
        this.id = donHang.getId();
        this.maDonHang = donHang.getMaDonHang();
        this.ngayDat = donHang.getNgayDat();
        this.tongTien = donHang.getTongTien();
        this.tenKhachHang = donHang.getKhachHang().getTenKhachHang();
        this.diaChi = donHang.getKhachHang().getDiaChi();
    }
}
