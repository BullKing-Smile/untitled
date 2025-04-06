package kafka;

import multipyThread.CommonUtil;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @auth Felix
 * @since 2025/3/3 1:24
 */
public class KafkaConsumerDemo {
    private static final String TOPIC = "topic_wsf";
    private static final String BOOTS_SERVER = "localhost:9092";
    public static void main(String[] args) {

        Properties properties = new Properties();
        // kafka 地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTS_SERVER);
        // 每个消费者要 指定一个group
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");

        // 可以设置 自动提交， 防止忘记调用commitSync()
        // default true
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        // key 序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // value 序列化
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(TOPIC));

        while (true) {
            // 拉取消息
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofNanos(100));
            for (ConsumerRecord<String, String> record : records) {
                CommonUtil.printMessage("partition = " + record.partition() +", offset = "+ record.offset() +", key = " + record.key() +", value = " + record.value());
            }

            // 提交offset, 告诉服务端 该消息已经处理完毕， 消息不会重复推送
            // 同步提交, 表示， 必须等到提交完成 再去消费下一批数据消息
            consumer.commitSync();

            // 异步提交， 表示发送完提交offset请求后， 就开始消费下一批数据
            // 不用等待Broker的确认
//            consumer.commitAsync();
        }

    }
}
