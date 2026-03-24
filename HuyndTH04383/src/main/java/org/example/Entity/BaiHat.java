package org.example.Entity;

public class BaiHat {
    private String ma;
    private String ten;
    private String tenCaSi;
    private Float thoiLuong;
    private String theLoai;

    public BaiHat(String ma, String ten, String tenCaSi, Float thoiLuong, String theLoai) {
        this.ma = ma;
        this.ten = ten;
        this.tenCaSi = tenCaSi;
        this.thoiLuong = thoiLuong;
        this.theLoai = theLoai;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenCaSi() {
        return tenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        this.tenCaSi = tenCaSi;
    }

    public Float getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(Float thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }
}
