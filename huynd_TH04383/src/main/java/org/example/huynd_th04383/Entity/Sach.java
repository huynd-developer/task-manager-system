package org.example.huynd_th04383.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "sach")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Mã sách không đuọc để trống")
    @Column(name = "ma_sach")
    private String maSach;
    @NotBlank(message = "Tên sách không đuọc để trống")
    @Column(name = "ten_sach")
    private String tenSach;
    @NotBlank(message = "Thể loại không đuọc để trống")
    @Column(name = "the_loai")
    private  String theLoai;
    @Column(name = "ngay_xuat_ban")
    private Date ngayXuatBan;
    @NotNull(message = "Giá không đuọc để trống")
    @Column(name = "gia")
    private Double gia;
    @NotNull(message = "Vui lòng chọn tác giả")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tac_gia")
    @ToString.Exclude
    private TacGia tacGia;
}
