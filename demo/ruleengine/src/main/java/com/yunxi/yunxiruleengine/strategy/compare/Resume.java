package com.yunxi.yunxiruleengine.strategy.compare;

import lombok.Data;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-17
 * @Description:
 */
@Data
public class Resume {
    private int id;
    private String name;
    private int age;
    private int salary;
    private int workYears;

    public Resume(int id, String name, int age, int salary, int workYears) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.workYears = workYears;
    }
}
