@Bean
public ProducerFactory<String, MessageEnvelopeDto> producerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.RETRIES_CONFIG, 3);
    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
    return new DefaultKafkaProducerFactory<>(props);
}

@Bean
public KafkaTemplate<String, MessageEnvelopeDto> kafkaTemplate(ProducerFactory<String, MessageEnvelopeDto> pf) {
    // configure JsonSerializer to not add type headers if desired:
    JsonSerializer<MessageEnvelopeDto> serializer = new JsonSerializer<>();
    serializer.setAddTypeInfo(false);
    return new KafkaTemplate<>(pf);
}
