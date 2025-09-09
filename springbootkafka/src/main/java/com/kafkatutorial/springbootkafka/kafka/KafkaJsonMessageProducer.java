package com.kafkatutorial.springbootkafka.kafka;

import com.kafkatutorial.springbootkafka.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonMessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonMessageProducer.class);

    private KafkaTemplate<String, User> kafkaTemplate;

    public KafkaJsonMessageProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User messageData){

        LOGGER.info(String.format("Message sent: %s", messageData.toString()));

        Message<User> message = MessageBuilder.withPayload(messageData).setHeader(KafkaHeaders.TOPIC, "jsonMessageKafkaTopic").build();

        kafkaTemplate.send(message);
    }
}
