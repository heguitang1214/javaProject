//package org.example;

public class Foo {
    public static native void foo();

    public native void bar(int i, long j);

    public native void bar(String s, Object o);

    static {
        System.loadLibrary("foo");
    }

    public static void main(String[] args) {
        new Foo().bar("", "");
    }

}
