package com.tang.protostuff;

import java.util.Arrays;

public class TestProtostuff {
    public static void main(String[] args) {
        Person person = new Person();
        person.setId(1);
        person.setName("张三");
        person.setEmail("test@yunxi.ai");

        // 序列化
        byte[] data = SerializationUtil.serialize(person);

        System.out.println("序列化后的结果：" + Arrays.toString(data));

        //反序列化
        Person desPerson = SerializationUtil.deserialize(data, Person.class);
        System.out.println("反序列化后的结果：" + desPerson.getName() + ": " + desPerson.getEmail());
    }
}
