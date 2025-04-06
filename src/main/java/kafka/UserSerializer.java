package kafka;

import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @auth Felix
 * @since 2025/3/3 22:23
 */
public class UserSerializer implements Serializer<User> {
    @Override
    public byte[] serialize(String topic, User data) {
        byte[] nameByte = data.getName().getBytes(StandardCharsets.UTF_8);

        int cap = 4 + 4 + 1 + nameByte.length + 4;
        byte disable = (byte) (data.getDisable() ? 0 : 1);
        ByteBuffer buffer = ByteBuffer.allocate(cap);
        buffer.putInt(data.getId()); // 4
        buffer.putInt(data.getAge()); // 4
        buffer.put(disable); // 1
        buffer.putInt(nameByte.length); // 4
        buffer.put(nameByte);
        return buffer.array();
    }
}
