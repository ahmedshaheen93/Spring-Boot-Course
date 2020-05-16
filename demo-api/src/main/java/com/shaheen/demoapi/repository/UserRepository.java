package com.shaheen.demoapi.repository;


import com.shaheen.demoapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> xyz(@Param("email") String mail, @Param("lastName") String lastName);
}
