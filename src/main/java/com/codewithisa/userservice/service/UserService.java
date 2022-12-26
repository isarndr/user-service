package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    User saveUser(User user) throws Exception;

    User getUserById(Long id) throws Exception;

    User updateUser(User user, Long id);

    void deleteUserById(Long id) throws Exception;

    User getUserByUsername(String username) throws Exception;

    Boolean existsByUsername(String username);

    Boolean existsByEmailAddress(String email);

    Boolean existsById(Long id);
}

