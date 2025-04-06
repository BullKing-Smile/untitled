package kafka;

import multipyThread.CommonUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @auth Felix
 * @since 2025/3/3 1:24
 */
public class KafkaProductorDemo {
    public static final String TOPIC = "topic_wsf";
    private static final String BOOTS_SERVER = "localhost:9092";
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties properties = new Properties();
        // kafka 地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTS_SERVER);
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "kafka.MyInterceptor");
        // key 序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // value 序列化
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.RoundRobinPartitioner");

        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");

        Producer<String, String> producer = new KafkaProducer<>(properties);


        for (int i = 0 ;i < 10; i++) {
            // 配置消息
            ProducerRecord<String, String> records = new ProducerRecord<>(TOPIC, "MyProducer-"+i);
            // 发送消息
            producer.send(records);
        }
        CommonUtil.printMessage("消息发送成功-1000");
        ProducerRecord<String, String> finalRecords = new ProducerRecord<>(TOPIC, "MyProducer-Final");

        RecordMetadata recordMetadata = producer.send(finalRecords).get();
        CommonUtil.printMessage("消息发送成功！topic="+recordMetadata.topic()+";partition="+recordMetadata.partition()+";keysize="+recordMetadata.serializedKeySize()+";valuesize="+recordMetadata.serializedValueSize());
    }
}
