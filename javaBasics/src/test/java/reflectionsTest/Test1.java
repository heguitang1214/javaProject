package reflectionsTest;


import utils.ReflectionsUtils;

public class Test1 {

    public static void main(String[] args) {
        A a = new A();
        ReflectionsUtils.invokeSetter(a, "name", "何贵堂");
        Object o = ReflectionsUtils.invokeGetter(a, "name");
        System.out.println(o);
    }

    static class A{
        private Integer id;
        private String name;

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
    }

}
