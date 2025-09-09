package com.kafkatutorial.springbootkafka.controller;

import com.kafkatutorial.springbootkafka.kafka.KafkaJsonMessageProducer;
import com.kafkatutorial.springbootkafka.payload.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class JsonMessageController {

    private KafkaJsonMessageProducer kafkaJsonMessageProducer;

    public JsonMessageController(KafkaJsonMessageProducer kafkaJsonMessageProducer) {
        this.kafkaJsonMessageProducer = kafkaJsonMessageProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody User user){
        kafkaJsonMessageProducer.sendMessage(user);
        return ResponseEntity.ok("JSON Message sent to the kafka topic!!");
    }
}
