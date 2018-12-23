package jvm.jdkTools;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11256 on 2018/9/12.
 * 堆内存溢出
 * 使用Java工具jmap分析堆内存溢出
 */
public class HeapOutOfMemoryError {

    public static void main(String[] args) {
        test();
    }

    /**
     * -Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
     */
    private static void test(){
        Map<String, Student> map = new HashMap<>( );
        Object[] array = new Object[500000];
        for (int i = 0; i < array.length; i++){
            Student s = new Student("test" + i, 100);
            map.put("test" + i, s);
            array[i] = s;
        }
        System.out.println("map的长度:" + map.size());
    }

    static class Student{
        private String name;
        private Integer age;

        Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
