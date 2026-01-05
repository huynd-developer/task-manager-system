package com.example.demo.Request;

import com.example.demo.Entity.KhachHang;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DonHangRequest {
    private Integer id;

    @NotBlank(message = "Ma don hang ko dc de trong")
    private String maDonHang;

    @NotBlank(message = "Ngay dat hang ko dc de trong")
    private String ngayDat;

    @NotNull(message = "Tong tien ko dc de trong")
    @Min(value = 0, message = "Tong tien phai lon hon 0")
    private Float tongTien;

    private KhachHang khachHang;
}
