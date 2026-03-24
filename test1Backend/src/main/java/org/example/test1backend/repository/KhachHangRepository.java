package org.example.test1backend.repository;

import org.example.test1backend.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepository extends JpaRepository<KhachHang,String> {
}
