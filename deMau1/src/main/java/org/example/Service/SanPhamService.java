package org.example.Service;

import org.example.Entity.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamService {
    protected List<SanPham> danhSachSanPham = new ArrayList<>();

    public void themSanPham(SanPham newSanPham){
        if (newSanPham == null){
            throw new IllegalArgumentException("Đối tượng sản phẩm không được rỗng");
        }
        if (newSanPham.getMa() == null || newSanPham.getMa().isEmpty() || newSanPham.getTen() == null || newSanPham.getTen().isEmpty()|| newSanPham.getDanhMuc() == null || newSanPham.getDanhMuc().isEmpty()){
            throw new IllegalArgumentException("Các thuộc tính không được để trống");
        }
        if(newSanPham.getSoluong() < 1 || newSanPham.getSoluong() > 100){
            throw new IllegalArgumentException("Số lượng phải nằm trong phạm vi từ 1 - 100");
        }
        if (newSanPham.getGia() <= 0){
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        }

        for(SanPham sp : danhSachSanPham){
            if(sp.getMa().equals(newSanPham.getMa())){
                throw new IllegalArgumentException("Mã sản phẩm đã tồn tại: " + newSanPham.getMa());
            }
        }

        danhSachSanPham.add(newSanPham);
    }

}
