package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Users;
import com.codewithisa.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    @Override
    public Users getUserByUsername(String username) {
        log.info("Inside getUserByUsername of UserServiceImpl");
        Optional<Users> user = Optional.ofNullable(userRepository.getUserByUsername(username));
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public Boolean existsByUsername(String username) {
        log.info("Inside existsByUsername of UserServiceImpl");
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmailAddress(String email) {
        return userRepository.existsByEmailAddress(email);
    }
}

