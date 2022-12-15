package com.codewithisa.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

//    @Bean
//    public NewTopic addUserTopic(){
//        return TopicBuilder.name("add-user")
//                .build();
//    }
}
