package com.codewithisa.userservice.kafka;

import com.codewithisa.userservice.entity.User;
import com.codewithisa.userservice.entity.request.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonKafkaProducer {

    @Value("${spring.kafka.topic-save-user.name}")
    private String saveUser;

    @Autowired
    private KafkaTemplate<String, SignupRequest> kafkaTemplate;

    public void sendMessage(SignupRequest signupRequest){

        Message<SignupRequest> message = MessageBuilder
                .withPayload(signupRequest)
                .setHeader(KafkaHeaders.TOPIC, saveUser)
                .build();

        kafkaTemplate.send(message);

        log.info("Message sent -> {}", signupRequest);
    }
}
