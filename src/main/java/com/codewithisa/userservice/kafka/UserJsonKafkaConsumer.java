package com.codewithisa.userservice.kafka;

import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserJsonKafkaConsumer {

    @Autowired
    UserService userService;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(User user){
        log.info("Json message recieved -> {}", user.toString());
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
