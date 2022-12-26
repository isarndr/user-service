package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) throws Exception{
        Boolean usernameExist = existsByUsername(user.getUsername());
        if(usernameExist) {
            log.error("Error: Username is already taken");
            throw new Exception("Error: Username is already taken");
        }

        Boolean emailExist = existsByEmailAddress(user.getEmailAddress());
        if(emailExist) {
            log.error("Error: Email is already taken");
            throw new Exception("Error: Email is already taken");
        }
        userRepository.save(user);
        log.info("user saved successfully");
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception{
        Boolean idExist = existsById(id);
        if(!idExist){
            log.error("id is not found");
            throw new Exception("id is not found");
        }
        log.info("User with id: {} is found", id);
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(User user, Long id) {
        User existingUser = userRepository.findById(id).get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmailAddress(user.getEmailAddress());
        existingUser.setPassword(user.getPassword());
        existingUser.setId(id);
        existingUser.setRoles(user.getRoles());
        userRepository.save(existingUser);
        log.info("user successfully updated");
        return existingUser;
    }


    @Override
    public void deleteUserById(Long id) throws Exception{
        Boolean idExist = existsById(id);
        if(!idExist){
            log.error("id is not found");
            throw  new Exception("id = "+id+" is not found");
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully");
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        Optional<User> user = Optional.ofNullable(userRepository.getUserByUsername(username));
        if(user.isPresent()){
            log.info("User with username: {} is found", username);
            return user.get();
        }
        log.error("User with username: {} is not found", username);
        throw new Exception("User with username: " + username + " is not found");
    }

    @Override
    public Boolean existsByUsername(String username) {
        log.info("User with username: {} is exist", username);
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmailAddress(String email) {
        log.info("User with email address: {} is found", email);
        return userRepository.existsByEmailAddress(email);
    }

    @Override
    public Boolean existsById(Long id) {
        log.info("User with id: {} is found", id);
        return userRepository.existsById(id);
    }
}

