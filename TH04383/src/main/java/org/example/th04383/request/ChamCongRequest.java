package org.example.th04383.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.th04383.entity.NhanVien;

@Data
public class ChamCongRequest {
    private Integer id;
    @NotBlank(message = "Ngay cham khong duoc de trong")
    private String ngayCham;
    @NotNull(message = "So gio lam khong duoc trong")
    @Min(value = 0,message = "So gio lam phai lon hon 0")
    private Float soGioLam;
    @NotNull(message = "Phat khong duoc trong")
    @Min(value = 0,message = "Phat khong duoc de trong")
    private Float phat;
    @NotNull(message = "Nhan vien khong duoc de trong")
    private NhanVien nhanVien;
}
