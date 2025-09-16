package com.kafkaproj.kafkaa.config;

import com.kafkaproj.kafkaa.dto.CorporateActionDistributionDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${app.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.default-topic:corporate-action-distributions}")
    private String defaultTopic;

    @Bean
    public ProducerFactory<String, CorporateActionDistributionDto> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // safe defaults; user can override in application.yml
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // wait for all replicas
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, CorporateActionDistributionDto> kafkaTemplate(ProducerFactory<String, CorporateActionDistributionDto> pf) {
        return new KafkaTemplate<>(pf);
    }

    // Optionally create topic if the broker supports auto topic create
    @Bean
    @ConditionalOnProperty(prefix = "app.kafka", name = "create-topic", havingValue = "true", matchIfMissing = false)
    public NewTopic defaultTopic() {
        return new NewTopic(defaultTopic, 3, (short) 1);
    }
}

