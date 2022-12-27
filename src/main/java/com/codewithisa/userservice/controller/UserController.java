package com.codewithisa.userservice.controller;

import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.entity.request.SignupRequest;
import com.codewithisa.userservice.kafka.JsonKafkaProducer;
import com.codewithisa.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private JsonKafkaProducer jsonKafkaProducer;

    @Value("${spring.kafka.topic-save-user.name}")
    private String saveUserTopic;

    @Value("${spring.kafka.topic-update-user.name}")
    private String updateUserTopic;

    @Value("${spring.kafka.topic-send-string.name}")
    private String sendStringTopic;

    @Value("${spring.kafka.topic-send-user.name}")
    private String sendUserTopic;

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

        jsonKafkaProducer.sendMessage(signupRequest, saveUserTopic);

        return new ResponseEntity<>("User Json message sent to topic: "+ saveUserTopic, HttpStatus.OK);
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

        User user = new User(id, signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());

        jsonKafkaProducer.sendMessageUser(user, sendUserTopic);

        return new ResponseEntity<>("Json sent to: "+sendUserTopic, HttpStatus.OK);
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

