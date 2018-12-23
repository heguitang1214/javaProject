package jvm.classLoader;

class Dog {
    Dog() {
        System.out.println("Dog is loader by : " + this.getClass().getClassLoader());
    }
}
