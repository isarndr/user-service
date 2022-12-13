package com.codewithisa.userservice.controller;

import com.codewithisa.userservice.entity.Role;
import com.codewithisa.userservice.entity.Users;
import com.codewithisa.userservice.entity.enumeration.ERoles;
import com.codewithisa.userservice.entity.request.SignupRequest;
import com.codewithisa.userservice.entity.response.MessageResponse;
//import com.codewithisa.userservice.service.KafkaProducer;
import com.codewithisa.userservice.service.RoleService;
import com.codewithisa.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
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

//    @Autowired
//    private KafkaProducer kafkaProducer;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @PostMapping("/add-user")
    public ResponseEntity<Users> saveUser(@RequestBody SignupRequest signupRequest) {
        log.info("Inside saveUser of UserController");
        Boolean usernameExist = userService.existsByUsername(signupRequest.getUsername());
        if(Boolean.TRUE.equals(usernameExist)) {
            log.error("Error: Username is already taken");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Boolean emailExist = userService.existsByEmailAddress(signupRequest.getEmail());
        if(Boolean.TRUE.equals(emailExist)) {
            log.error("Error: Email is already taken");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Users user = new Users(signupRequest.getUsername(), signupRequest.getEmail(),
//                passwordEncoder.encode(signupRequest.getPassword()));
                signupRequest.getPassword());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role role = roleService.findByName(ERoles.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                Role roles1 = roleService.findByName(ERoles.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
                roles.add(roles1);
            });
        }
        user.setRoles(roles);
//        kafkaProducer.sendMessage(user);
//        log.info("Message sent to kafka topic");
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-user-id/{userId}")
    public ResponseEntity<Users> getUserByUserId(@PathVariable("userId") Long userId){
        log.info("Inside getUser of UserController");
        Boolean userIdExist = userService.existsById(userId);
        if(!userIdExist){
            log.error("userId is not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.getUserByUserId(userId),HttpStatus.OK);
    }

    @Operation(
            summary = "untuk mengubah user yang ada di database"
    )
    @PutMapping("update-user-by-user-id/{userId}")
    public ResponseEntity<Users> updateUser(@Schema(example = "1") @PathVariable("userId") Long userId,
                                                      @Valid @RequestBody SignupRequest signupRequest){
        log.info("Inside updateUser of UserController");

        Boolean userIdExist = userService.existsById(userId);
        if(!userIdExist){
            log.error("userId is not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Boolean usernameExist=userService.existsByUsername(signupRequest.getUsername());
        if(usernameExist){
            log.error("username already exist");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Users users = new Users(signupRequest.getUsername(), signupRequest.getEmail(),
//                passwordEncoder.encode(signupRequest.getPassword()));
                signupRequest.getPassword());
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role role = roleService.findByName(ERoles.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                Role roles1 = roleService.findByName(ERoles.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
                roles.add(roles1);
            });
        }
        users.setRoles(roles);

        try{
            log.info("user updated successfully");
            return new ResponseEntity<>(userService.updateUser(users, userId), HttpStatus.OK);
        }
        catch (Exception e){
            log.error("username or email already regestered, please input something else");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "untuk menghapus user dari database"
    )
    @DeleteMapping("/delete-user-by-user-id/{userId}")
    public ResponseEntity<String> deleteUser(@Schema(example = "1")  @PathVariable("userId") Long userId){
        log.info("Inside deleteUser of UserController");
        userService.deleteUserByUserId(userId);
        return new ResponseEntity<>("user successfully deleted",HttpStatus.OK);
    }

    @Operation(summary = "untuk menampilkan user berdasarkan username")
    @GetMapping("get-user-by-username/{username}")
    public ResponseEntity<Users> getUserByUsername(@Schema(example = "isarndr")@PathVariable("username") String username){
        log.info("Inside getUserByUsername of UserController");
        Boolean usernameExist = userService.existsByUsername(username);
        if(!usernameExist){
            log.error("username is not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Users>(userService.getUserByUsername(username),HttpStatus.OK);
    }
}

