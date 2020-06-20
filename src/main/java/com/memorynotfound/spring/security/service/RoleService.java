package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role findByName(String name);
    Role addNewRole(String name);
}
