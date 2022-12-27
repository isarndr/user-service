package com.codewithisa.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic-save-user.name}")
    private String saveUserTopic;

    @Value("${spring.kafka.topic-update-user.name}")
    private String updateUserTopic;

    @Value("${spring.kafka.topic-send-string.name}")
    private String sendStringTopic;

    @Value("${spring.kafka.topic-send-user.name}")
    private String sendUserTopic;

    @Bean
    public NewTopic saveUser(){
        return TopicBuilder.name(saveUserTopic)
                .build();
    }

    @Bean
    public NewTopic updateUser(){
        return TopicBuilder.name(updateUserTopic)
                .build();
    }

    @Bean
    public NewTopic sendString(){
        return TopicBuilder.name(sendStringTopic)
                .build();
    }

    @Bean
    public NewTopic sendUser(){
        return TopicBuilder.name(sendUserTopic)
                .build();
    }
}