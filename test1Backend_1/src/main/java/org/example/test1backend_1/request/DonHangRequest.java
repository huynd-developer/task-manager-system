package org.example.test1backend_1.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.test1backend_1.entity.KhachHang;

@Data
public class DonHangRequest {
    private Integer id;
    @NotBlank(message = "Mã đơn hàng không được trống")
    private String maDonHang;
    @NotBlank(message = "Ngày đặt không được trống")
    private String ngayDat;
    @NotNull(message = "Tổng tiền không được trống")
    @Min(value = 0,message = "Tổng tiền phải lớn hơn 0")
    private Float tongTien;
    @NotNull(message = "Khách hàng không được trống")
    private KhachHang khachHang;
}
