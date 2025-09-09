package com.kafkatutorial.springbootkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic firstKafkaTopic(){
        return TopicBuilder.name("myfirstKafkaTopic").build();
    }

    @Bean
    public NewTopic jsonMessageKafkaTopic(){
        return TopicBuilder.name("jsonMessageKafkaTopic").build();
    }

}
