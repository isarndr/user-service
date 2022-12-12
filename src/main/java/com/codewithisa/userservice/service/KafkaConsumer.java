package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "add-user",
            groupId = "group-1")
    public void consume(Users user){
        log.info("Inside consume of KafkaConsumer");
        LOGGER.info(String.format("Message received -> %s", user.toString()));
        userService.saveUser(user);
    }
}
