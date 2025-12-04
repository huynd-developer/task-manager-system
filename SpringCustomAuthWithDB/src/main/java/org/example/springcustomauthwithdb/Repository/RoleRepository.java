package org.example.springcustomauthwithdb.Repository;

import org.example.springcustomauthwithdb.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
