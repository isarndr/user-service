package com.codewithisa.userservice.kafka;

import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.entity.request.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonKafkaProducer {

    @Autowired
    private KafkaTemplate<String, SignupRequest> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplateSendUser;

    public void sendMessageSignupRequest(SignupRequest signupRequest, String topicName){

        Message<SignupRequest> message = MessageBuilder
                .withPayload(signupRequest)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        kafkaTemplate.send(message);

        log.info("Message sent to topic: {} -> {}", topicName, signupRequest);
    }

    public void sendMessageUser(User user, String topicName){

        Message<User> message = MessageBuilder
                .withPayload(user)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        kafkaTemplate.send(message);

        log.info("Message sent to topic: {} -> {}", topicName, user);
    }
}
