package com.kafkatutorial.springbootkafka.kafka;

import com.kafkatutorial.springbootkafka.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonMessageConsumer.class);

    @KafkaListener(topics = "jsonMessageKafkaTopic", groupId = "myGroup")
    public void consume(User user){
        LOGGER.info(String.format("JSON message received: %s", user.toString()));
    }

}
