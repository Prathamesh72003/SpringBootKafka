package com.kafkaproj.kafkaa.producer;

import com.kafkaproj.kafkaa.dto.CorporateActionDistributionDto;
import com.kafkaproj.kafkaa.entity.CorporateActionDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class DistributionKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(DistributionKafkaProducer.class);
    private final KafkaTemplate<String, CorporateActionDistributionDto> kafkaTemplate;

    public DistributionKafkaProducer(KafkaTemplate<String, CorporateActionDistributionDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, CorporateActionDistributionDto>> send(
            String topic, String key, CorporateActionDistributionDto value) {

        log.debug("Sending to topic={} key={} id={}", topic, key, value.getId());
        return kafkaTemplate.send(topic, key, value);
    }
}
