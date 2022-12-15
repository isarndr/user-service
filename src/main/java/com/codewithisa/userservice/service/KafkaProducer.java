package com.codewithisa.userservice.service;


import com.codewithisa.userservice.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Users> kafkaTemplate;

//    public void sendMessage(Users user){
//        log.info("Inside sendMessage of KafkaProducer");
//        LOGGER.info(String.format("Message sent -> %s", user.toString()));
//
//        Message<Users> message = MessageBuilder
//                .withPayload(user)
//                .setHeader(KafkaHeaders.TOPIC, "add-user")
//                .build();
//
//        kafkaTemplate.send(message);
//    }
}
