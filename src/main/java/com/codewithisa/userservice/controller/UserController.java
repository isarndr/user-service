package com.codewithisa.userservice.controller;

import com.codewithisa.userservice.entity.Role;
import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.entity.enumeration.ERoles;
import com.codewithisa.userservice.entity.request.SignupRequest;
//import com.codewithisa.userservice.service.KafkaProducer;
//import com.codewithisa.userservice.service.KafkaProducer;
import com.codewithisa.userservice.service.RoleService;
import com.codewithisa.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

//    @Autowired
//    private KafkaProducer kafkaProducer;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody SignupRequest signupRequest) {
        try{
            Set<String> strRoles = signupRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if(strRoles == null) {
                Role role = roleService.findByName(ERoles.CUSTOMER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                roles.add(role);
            }
            else {
                strRoles.forEach(role -> {
                    Role roles1 = roleService.findByName(ERoles.valueOf(role))
                            .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
                    roles.add(roles1);
                });
            }

            User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
//                passwordEncoder.encode(signupRequest.getPassword()));
                    signupRequest.getPassword(), roles);

//        kafkaProducer.sendMessage(user);
//        log.info("Message sent to kafka topic");
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "untuk mengubah user yang ada di database"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Schema(example = "1") @PathVariable("id") Long id,
                                           @Valid @RequestBody SignupRequest signupRequest){
        Boolean idExist = userService.existsById(id);

        if(!idExist){
            log.error("id is not found");
            return new ResponseEntity<>("id is not found",HttpStatus.BAD_REQUEST);
        }

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role role = roleService.findByName(ERoles.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(role);
        }
        else {
            strRoles.forEach(role -> {
                Role roles1 = roleService.findByName(ERoles.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
                roles.add(roles1);
            });
        }

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
//                passwordEncoder.encode(signupRequest.getPassword()));
                signupRequest.getPassword(), roles);

        try{
            user = userService.updateUser(user, id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            log.error("username or email already regestered, please input something else");
            return new ResponseEntity<>("username or email already regestered, please input something else",HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "untuk menghapus user dari database"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@Schema(example = "1")  @PathVariable("id") Long id){
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>("user deleted successfully",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }

    @Operation(summary = "untuk menampilkan user berdasarkan username")
    @GetMapping()
    public ResponseEntity<?> getUserByUsername(@Schema(example = "isarndr")@RequestParam("username") String username){
        try {
            return new ResponseEntity<User>(userService.getUserByUsername(username),HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Username " + username + " is not found", HttpStatus.BAD_REQUEST);
        }
    }
}

