import com.example.cad.dto.MessageEnvelopeDto;
import com.example.cad.producer.DistributionKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ProducerService {
    private final DistributionKafkaProducer producer;
    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);
    private final Duration sendTimeout = Duration.ofSeconds(10);

    public ProducerService(DistributionKafkaProducer producer) {
        this.producer = producer;
    }

    public void publishEnvelopes(List<MessageEnvelopeDto> envelopes, String topic) {
        if (envelopes == null || envelopes.isEmpty()) return;

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(envelopes.size(), 8));
        try {
            List<CompletableFuture<Void>> futures = envelopes.stream().map(env ->
                CompletableFuture.runAsync(() -> {
                    try {
                        producer.send(topic, env.getMessageId(), env).get(sendTimeout.toMillis(), TimeUnit.MILLISECONDS);
                    } catch (ExecutionException ee) {
                        log.error("Send failed for messageId={}", env.getMessageId(), ee.getCause());
                        throw new CompletionException(new KafkaException("publish failed", ee.getCause()));
                    } catch (TimeoutException te) {
                        log.error("Send timed out for messageId={}", env.getMessageId(), te);
                        throw new CompletionException(new KafkaException("publish timed out", te));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new CompletionException(new KafkaException("publish interrupted", ie));
                    }
                }, executor)
            ).toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get(sendTimeout.toMillis(), TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new KafkaException("Interrupted waiting for sends", e);
        } catch (ExecutionException | TimeoutException e) {
            throw new KafkaException("Failed sending messages", e);
        } finally {
            executor.shutdownNow();
        }
    }
}
