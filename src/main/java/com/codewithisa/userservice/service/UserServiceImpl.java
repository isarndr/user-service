package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Users;
import com.codewithisa.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public Users updateUser(Users user, Long userId) {
        log.info("Inside updateUser of UserServiceImpl");
        Users existingUser = userRepository.findById(userId).get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmailAddress(user.getEmailAddress());
        existingUser.setPassword(user.getPassword());
        existingUser.setUserId(userId);
        userRepository.save(existingUser);
        log.info("user successfully updated");
        return existingUser;
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        log.info("Inside deleteUserByUserId of UserServiceImpl");
        userRepository.deleteUserByUserId(userId);
    }
}

