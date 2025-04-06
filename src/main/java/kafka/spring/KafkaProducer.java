package kafka.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @auth Felix
 * @since 2025/3/5 1:20
 */

public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Integer partition, String key, Object value) {
        kafkaTemplate.send(topic, partition, key, value);
    }
}
