package com.codewithisa.userservice.controller;

import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.entity.request.SignupRequest;
//import com.codewithisa.userservice.service.KafkaProducer;
//import com.codewithisa.userservice.service.KafkaProducer;
import com.codewithisa.userservice.kafka.UserJsonKafkaProducer;
import com.codewithisa.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserJsonKafkaProducer userJsonKafkaProducer;


    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody SignupRequest signupRequest) {

        boolean emailAlreadyExist = userService.existsByEmailAddress(signupRequest.getEmail());

        if(emailAlreadyExist){
            log.error("Email already exist");
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        }

        boolean usernameAlreadyExist = userService.existsByUsername(signupRequest.getUsername());

        if(usernameAlreadyExist){
            log.error("Username already exist");
            return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
        }

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());

        userJsonKafkaProducer.sendMessage(user);

        return new ResponseEntity<>("User Json message sent to kafka topic", HttpStatus.OK);
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

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                signupRequest.getPassword());

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

