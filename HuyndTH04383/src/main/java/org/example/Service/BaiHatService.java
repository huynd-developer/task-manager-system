package org.example.Service;

import org.example.Entity.BaiHat;

import java.util.ArrayList;
import java.util.List;

public class BaiHatService {
    private List<BaiHat> danhSachBaiHat = new ArrayList<>();

    public boolean themBaiHat(BaiHat baiHat) {
        if (baiHat.getMa() == null || baiHat.getMa().trim().isEmpty() ||
                baiHat.getTen() == null || baiHat.getTen().trim().isEmpty() ||
                baiHat.getTenCaSi() == null || baiHat.getTenCaSi().trim().isEmpty() ||
                baiHat.getTheLoai() == null || baiHat.getTheLoai().trim().isEmpty()) {
            throw new IllegalArgumentException("Tất cả các trường không được để trống");
        }

        if (baiHat.getThoiLuong() < 2.0f || baiHat.getThoiLuong() > 5.99f) {
            throw new IllegalArgumentException("Thời lượng phải trong khoảng 2:00 - 5:59 phút");
        }

        for (BaiHat bh : danhSachBaiHat) {
            if (bh.getMa().equals(baiHat.getMa())) {
                throw new IllegalArgumentException("Mã bài hát đã tồn tại");
            }
        }

        return danhSachBaiHat.add(baiHat);
    }

    public List<BaiHat> getDanhSachBaiHat() {
        return new ArrayList<>(danhSachBaiHat);
    }

    public int soLuongBaiHat() {
        return danhSachBaiHat.size();
    }
}
