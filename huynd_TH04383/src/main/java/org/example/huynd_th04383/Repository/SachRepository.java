package org.example.huynd_th04383.Repository;

import org.example.huynd_th04383.Entity.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SachRepository extends JpaRepository<Sach, Integer>
{
    Page<Sach> findAll(Pageable pageable);
    Page<Sach> findByTenSachContaining(String keyword, Pageable pageable);
}
