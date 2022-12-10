package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Users;
import com.codewithisa.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public Users saveUser(Users user) {
        log.info("Inside saveUser of UserServiceImpl");
        return userRepository.save(user);
    }

    @Override
    public Users getUserByUserId(Long userId) {
        log.info("Inside getUserByUserId of UserServiceImpl");
        return userRepository.findUserByUserId(userId);
    }
}

