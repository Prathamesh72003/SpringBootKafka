import com.example.cad.dto.MessageEnvelopeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class DistributionKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(DistributionKafkaProducer.class);
    private final KafkaTemplate<String, MessageEnvelopeDto> kafkaTemplate;

    public DistributionKafkaProducer(KafkaTemplate<String, MessageEnvelopeDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ListenableFuture<SendResult<String, MessageEnvelopeDto>> send(String topic, String key, MessageEnvelopeDto value) {
        log.debug("Sending envelope to topic={} key={} messageId={}", topic, key, value.getMessageId());
        return kafkaTemplate.send(topic, key, value);
    }
}
