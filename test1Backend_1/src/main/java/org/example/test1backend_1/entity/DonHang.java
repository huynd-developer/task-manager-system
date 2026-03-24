package org.example.test1backend_1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "don_hang")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ma_don_hang")
    private String maDonHang;
    @Column(name = "ngay_dat")
    private String ngayDat;
    @Column(name = "tong_tien")
    private Float tongTien;
    @ManyToOne
    @JoinColumn(name = "khach_hang_id",referencedColumnName = "id")
    private KhachHang khachHang;
}
