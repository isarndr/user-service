package com.codewithisa.userservice.kafka;

import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.entity.request.SignupRequest;
import com.codewithisa.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonKafkaConsumer {

    @Autowired
    UserService userService;

    @Value("${spring.kafka.topic-update-user.name}")
    private String updateUserTopic;

    @Value("${spring.kafka.topic-send-user.name}")
    private String sendUserTopic;

    @KafkaListener(topics = "${spring.kafka.topic-save-user.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void saveUser(SignupRequest signupRequest){

        log.info("Json message recieved from topic {} -> {}", "${spring.kafka.topic-save-user.name}", signupRequest.toString());

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic-send-user.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void updateUser(User user){

        log.info("Json message recieved from topic {} -> {}", sendUserTopic, user);
        try{
            userService.updateUser(user, user.getId());
        }
        catch (Exception e){
            log.error("username or email already regestered, please input something else");
        }

    }
}
