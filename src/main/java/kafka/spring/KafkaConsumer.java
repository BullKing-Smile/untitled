package kafka.spring;

import multipyThread.CommonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @auth Felix
 * @since 2025/3/5 1:20
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"topic1", "topic2"})
    public void onReceived(ConsumerRecord<?, ?> record) {
        CommonUtil.printMessage("收到消息：topic=" + record.topic() + ";Partition=" + record.partition() + ";key=" + record.key() + ";value=" + record.value());
    }
}
