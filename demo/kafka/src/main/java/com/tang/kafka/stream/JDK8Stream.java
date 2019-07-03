package com.tang.kafka.stream;

import java.util.ArrayList;
import java.util.List;

public class JDK8Stream {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(5);
        list.add(4);
        // list 输出排序好的序列
        // lambda表达式
        list.stream().sorted().forEach(System.out::println);
    }
}
