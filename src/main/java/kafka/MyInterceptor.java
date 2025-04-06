package kafka;

import multipyThread.CommonUtil;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @auth Felix
 * @since 2025/3/3 12:34
 */
public class MyInterceptor implements ProducerInterceptor {
    // 发送消息时触发
    // 该阶段 可以对消息进行修改 操作
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        CommonUtil.printMessage("收到生产者发送的消息："+producerRecord.toString());
        return producerRecord;
    }

    // 收到服务端响应时触发
    // 对于失败 可以处理重发机制
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        CommonUtil.printMessage("收到服务端响应-onAcknowledgement");

    }

    // 连接关闭时触发
    // 可以处理 关闭一些资源
    @Override
    public void close() {
        CommonUtil.printMessage("producer-close");
    }

    // 整理配置项
    @Override
    public void configure(Map<String, ?> map) {
        CommonUtil.printMessage("---configure start---");
        map.forEach((k, v)-> CommonUtil.printMessage("key="+k+";value="+v));
        CommonUtil.printMessage("---configure end---");
    }
}
