package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Role;
import com.memorynotfound.spring.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role addNewRole(String name) {
        Role erole = findByName(name);
        if(erole != null) return null;
        else{
            Role role = new Role();
            role.setName(name);
            roleRepository.save(role);
            return role;
        }
    }
}
