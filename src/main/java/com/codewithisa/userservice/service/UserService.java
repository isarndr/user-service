package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Users;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    Users saveUser(Users user);

    Users getUserByUserId(Long userId);
}

