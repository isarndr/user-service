package com.codewithisa.userservice.controller;

import com.codewithisa.userservice.entity.Users;
import com.codewithisa.userservice.entity.request.SignupRequest;
import com.codewithisa.userservice.entity.response.MessageResponse;
import com.codewithisa.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

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

    @Operation(
            summary = "untuk mengubah user yang ada di database"
    )
    @PutMapping("update-user/{userId}")
    public ResponseEntity<Users> updateUser(@Schema(example = "1") @PathVariable("userId") Long userId,
                                                      @Valid @RequestBody SignupRequest signupRequest){
        log.info("Inside updateUser of UserController");
        Users users = new Users(signupRequest.getUsername(), signupRequest.getEmail(),
                signupRequest.getPassword());

        return new ResponseEntity<>(userService.updateUser(users, userId), HttpStatus.OK);
    }
}

