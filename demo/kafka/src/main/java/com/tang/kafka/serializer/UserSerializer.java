package com.tang.kafka.serializer;

import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * 自定义的序列化
 */
public class UserSerializer implements Serializer<User> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, User user) {
        int size = 0;
        byte[] serializedName = new byte[0];

        if (user == null) {
            return null;
        }

        if (user.getName() != null) {
            try {
                serializedName = user.getName().getBytes("UTF-8");
                size = serializedName.length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + size);
        buffer.putInt(user.getId());
        buffer.putInt(size);
        buffer.put(serializedName);
        return buffer.array();
    }

    @Override
    public void close() {
    }
}
