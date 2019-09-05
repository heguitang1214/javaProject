package optional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Demo01 {

    public static void main(String[] args) {


        Optional<String> OptionalString = Optional.ofNullable("abc");
        System.out.println(OptionalString.map((v) -> v.toUpperCase()));//Optional[ABC]


        Optional<String> OptionalString1 = Optional.ofNullable("abc");
        //Optional.empty   v的长度小于4，返回空的Optional对象
        System.out.println(OptionalString1.filter((v) -> v.length() > 4));


        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        Optional<List<String>> optionalList = Optional.ofNullable(list);
        System.out.println(optionalList.get());


        OptionalObject optionalObject1 = new OptionalObject(1, "name1");
        OptionalObject optionalObject2 = new OptionalObject(2, "name2");
        OptionalObject optionalObject3 = new OptionalObject(3, "name3");
        OptionalObject optionalObject4 = new OptionalObject(4, "name4");

        List<OptionalObject> list1 = Arrays.asList(optionalObject1, optionalObject2, optionalObject3, optionalObject4);
        list1 = null;
        Optional<List<OptionalObject>> optionalObjectList = Optional.ofNullable(list1);
        System.out.println(optionalObjectList.isPresent());
        optionalObjectList.get().forEach(System.out::println);

//        Optional<Map<Integer, String>> map = Optional.ofNullable(list1.stream().collect(Collectors.toMap(OptionalObject::getId, OptionalObject::getName)));
//        System.out.println(map);

    }

    static class OptionalObject {

        private Integer id;
        private String name;

        public OptionalObject(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "OptionalObject{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
