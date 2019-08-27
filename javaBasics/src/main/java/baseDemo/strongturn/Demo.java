package baseDemo.strongturn;

import java.util.ArrayList;
import java.util.List;

/**
 * Java中的强转
 */
public class Demo {

    public static void main(String[] args) {
        // Parent是父类，Children是子类
        Parent parent = new Parent();
        parent.setName("parent");
        parent.sayParent(); // Parent类中的sayParent()...

        Children children = new Children();
        children.setName("children");
        children.setSex(true);
        children.sayParent(); // Children类中的sayParent()...
        children.sayChildren(); // Children类中的sayChildren()...

        // Java中的对象进行类型提升，依然保持其原有的类型。
        // 子类强转父类,其实仍然是子类
        Parent parent1 = (Parent) children;
        System.out.println(parent1.toString()); // B [name=children, sex=true]
        // 该引用只能调用父类中定义的方法和变量；
        // 报错：The method sayChildren() is undefined for the type Parent
        //parent1.sayChildren();

        // 如果子类中重写了父类中的一个方法，那么在调用这个方法的时候，将会调用子类中的这个方法；
        // parent1其实是Children，调用的是类Children的方法
        parent1.sayParent(); // Children类中的sayParent()...

        // 报错：java.lang.ClassCastException: baseDemo.strongturn.Demo$Parent cannot be cast to baseDemo.strongturn.Demo$Children
        // Children是父类，是不能【强转】成子类的
        //Children children1 = (Children) parent;
        // 只有父类对象本身就是用子类new出来的时候, 才可以在将来被强制转换为子类对象.
        // parent1其实是Children,可以转成Children
        Children children1 = (Children) parent1;
        System.out.println(children1.toString()); // B [name=children, sex=true]
        children1.sayParent(); // Children类中的sayParent()...
        children1.sayChildren(); // Children类中的sayChildren()...

        List<Parent> parentList = new ArrayList<>();
        parentList.add(parent);
        parentList.add(children);
        for (Parent item : parentList) {
            System.out.println(item.getClass() + ":" + item.toString());
            // Demo$Parent:A [name=parent]
            // Demo$Children:B [name=children, sex=true]
        }
    }


    public static class Parent {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void sayParent() {
            System.out.println("Parent类中的sayParent()...");
        }

        @Override
        public String toString() {
            return "A [name=" + name + "]";
        }
    }

    public static class Children extends Parent {

        private boolean sex;

        public boolean isSex() {
            return sex;
        }

        void setSex(boolean sex) {
            this.sex = sex;
        }

        @Override
        public void sayParent() {
            System.out.println("Children类中的sayParent()...");
        }

        void sayChildren() {
            System.out.println("Children类中的sayChildren()...");
        }

        @Override
        public String toString() {
            return "B [name=" + this.getName() + ", sex=" + sex + "]";
        }
    }
}
