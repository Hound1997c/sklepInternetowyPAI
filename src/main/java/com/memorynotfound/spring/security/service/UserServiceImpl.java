package com.memorynotfound.spring.security.service;

import com.memorynotfound.spring.security.model.Bucket;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.Role;
import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.repository.UserRepository;
import com.memorynotfound.spring.security.web.dto.UserEditDto;
import com.memorynotfound.spring.security.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    private RoleService roleService;*/

    @Autowired
    private BucketService bucketService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration, Role role){
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        //user.setRoles(Arrays.asList(new Role("ROLE_USER")));

        user.setRoles(Arrays.asList(role));
        return userRepository.save(user);
    }

    @Override
    public User addToBucket(User user, Product product) {
        //user.addToBucket(product);
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        bucket.setProduct(product);
        bucketService.save(bucket);
        return userRepository.saveAndFlush(user);
        //return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User changeInfo(User userToEdit, UserEditDto tempUser) {
        //not nulable values
        String tempName = tempUser.getFirstName();
        if(tempName!=null && !userToEdit.getFirstName().equals(tempName) && tempName.length()>=3){
            userToEdit.setFirstName(tempName);
        }
        String tempLName = tempUser.getLastName();
        if(tempLName!=null && !userToEdit.getLastName().equals(tempLName) && tempLName.length()>=3){
            userToEdit.setLastName(tempLName);
        }
        long tempMoney = tempUser.getMoney();
        if(tempLName!=null && tempMoney>=0){
            userToEdit.setMoney(tempMoney);
        }

        String tempPw = tempUser.getPassword();
        if(tempPw!=null && tempPw.length()>=3){
            userToEdit.setPassword(passwordEncoder.encode(tempPw));
        }
        userRepository.saveAndFlush(userToEdit);
        //if(!userToEdit.getLastName().equals())

        return userToEdit;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public String tryMySqlFunction() {
        return userRepository.tryMySqlFunction();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
