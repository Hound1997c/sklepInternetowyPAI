package com.memorynotfound.spring.security.repository;

import com.memorynotfound.spring.security.model.Role;
import com.memorynotfound.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
