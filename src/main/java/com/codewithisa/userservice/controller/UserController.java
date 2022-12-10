package com.codewithisa.userservice.controller;

import com.codewithisa.userservice.entity.Users;
import com.codewithisa.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/")
    public Users saveUser(@RequestBody Users user) {
        log.info("Inside saveUser of UserController");
        return userService.saveUser(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserByUserId(@PathVariable("userId") Long userId){
        log.info("Inside getUser of UserController");
        return new ResponseEntity<>(userService.getUserByUserId(userId),HttpStatus.OK);
    }
}

