package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.Role;
import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.web.dto.UserEditDto;
import com.memorynotfound.spring.security.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration, Role role);

    User addToBucket(User user, Product product);

    List<User> findAll();

    User changeInfo(User userToEdit, UserEditDto tempUser);

    void deleteUser(User user);

    User save(User user);

    String tryMySqlFunction();

}
