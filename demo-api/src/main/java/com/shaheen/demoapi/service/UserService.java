package com.shaheen.demoapi.service;

import com.shaheen.demoapi.model.Role;
import com.shaheen.demoapi.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(Long id);

    User save(User user, Role role);

    User update(User user);

    void delete(User user);

    List<User> findByEmailAndLastName(String email, String lastName);
}
