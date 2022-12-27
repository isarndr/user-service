package com.codewithisa.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic-save-user.name}")
    private String saveUser;

    @Bean
    public NewTopic saveUser(){
        return TopicBuilder.name(saveUser)
                .build();
    }
}