package org.example.huynd_th04383.Repository;

import org.example.huynd_th04383.Entity.TacGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacGiaRepository extends JpaRepository<TacGia,Long> {
}
