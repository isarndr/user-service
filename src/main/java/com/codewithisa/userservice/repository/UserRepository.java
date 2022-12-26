package com.codewithisa.userservice.repository;

import com.codewithisa.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmailAddress(String email);
}

