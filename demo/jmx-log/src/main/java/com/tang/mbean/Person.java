package com.tang.mbean;

public class Person implements PersonMBean {

    private String name;
    private int age;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void changeName(String name) {
        System.out.println("====== Change Name:" + name);
        this.name = name;
    }

    @Override
    public void changeAge(int age) {
        System.out.println("====== Change Age:" + age);
        this.age = age;

    }

    @Override
    public void changeAddress(String address) {
        System.out.println("====== Change Address:" + address);
        this.address = address;
    }

}
