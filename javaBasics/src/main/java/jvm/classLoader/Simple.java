package jvm.classLoader;

public class Simple {

    public int number = 1;

    public Simple() {
        System.out.println("Sample is loader by : " + this.getClass().getClassLoader());
        new Dog();
    }
}
