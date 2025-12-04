package org.example.springcustomauthwithdb.Repository;

import org.example.springcustomauthwithdb.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
