package com.zero.protostuff;

public class TestProtostuff {
    public static void main(String[] args) {
        Person person = new Person();
        person.setId(1);
        person.setName("张三");
        person.setEmail("test@yunxi.ai");

        // 序列化
        byte[] data = SerializationUtil.serialize(person);

        //反序列化
        Person desPerson = SerializationUtil.deserialize(data, Person.class);
        System.out.println(desPerson.getName() + ": " + desPerson.getEmail());
    }
}
