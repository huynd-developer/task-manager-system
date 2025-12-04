package org.example.huynd_th04383.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "tac_gia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TacGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ma_tac_gia")
    private String maTacGia;

    @Column(name = "ten_tac_gia")
    private String tenTacGia;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "tacGia")
    @ToString.Exclude
    private List<Sach> sach;
}
