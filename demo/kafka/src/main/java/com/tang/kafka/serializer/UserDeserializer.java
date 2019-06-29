package com.tang.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * 自定义的反序列化
 */
public class UserDeserializer implements Deserializer<User> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    @Override
    public User deserialize(String topic, byte[] data) {
        if (data == null)
            return null;

        if (data.length < 8)
            throw new SerializationException("数据格式不正确，无法序列化！");

        ByteBuffer buffer = ByteBuffer.wrap(data);
        int id = buffer.getInt();
        byte[] nameBytes = new byte[buffer.getInt()];
        buffer.get(nameBytes);

        try {
            String name = new String(nameBytes, "UTF-8");
            return new User(id, name);
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("反序列化时出错 name to byte[] " + e);
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}
