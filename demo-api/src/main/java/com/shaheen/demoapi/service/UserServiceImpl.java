package com.shaheen.demoapi.service;

import com.shaheen.demoapi.model.Role;
import com.shaheen.demoapi.model.User;
import com.shaheen.demoapi.repository.RoleRepository;
import com.shaheen.demoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User save(User user, Role role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role byRoleName = roleRepository.findByRoleName(role.getRoleName());

        if (byRoleName != null) {
            role = byRoleName;
            role.getUsers().add(user);
        } else {
            user.addRole(role);
        }
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User update(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findByEmailAndLastName(String email, String lastName) {
        return userRepository.xyz(email, lastName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUsername(userName);
    }
}
