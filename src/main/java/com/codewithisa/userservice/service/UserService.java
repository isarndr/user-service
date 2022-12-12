package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Users;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    Users saveUser(Users user);

    Users getUserByUserId(Long userId);

    Users updateUser(Users users, Long userId);

    void deleteUserByUserId(Long userId);

    Users getUserByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmailAddress(String email);
}

