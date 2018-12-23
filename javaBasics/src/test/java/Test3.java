import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 11256 on 2018/9/10.
 * 测试3
 */
public class Test3 {
    public static void main(String[] args) throws Exception {
        //Person按照年龄排序
        Person person1 = new Person("张三", 20);
        Person person2 = new Person("李四", 30);
        Person person3 = new Person("王五", 10);
        List<Person> personList = Arrays.asList(person1, person2, person3);
        System.out.println("排序前=====================");
        personList.forEach(System.out::println);
        System.out.println("排序后=====================");
//        Collections.sort(personList, (o1, o2) -> {
//            //自定义规则
//            return o1.getAge().compareTo(o2.getAge());
//        });
        //简写方式
        personList.sort(Comparator.comparing(Person::getAge));
        personList.forEach(System.out::println);
    }

    static class Person{
        private String name;
        private Integer age;

        public Person(String name, int age) {
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

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

    }
}
