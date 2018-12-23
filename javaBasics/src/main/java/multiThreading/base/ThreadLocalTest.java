package multiThreading.base;


import java.util.Random;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 * 本地线程:本地线程和对象绑定
 */
public class ThreadLocalTest {

    //本地线程Integer数据类型
    static ThreadLocal<Integer> x = new ThreadLocal<>();

    static ThreadLocal<MyThreadScopeData> localData = new ThreadLocal<>();

    public static void main(String[] args) {

        /*
          产生5个线程,然后为每个线程赋值
          MyThreadScopeData.getInstace().setAge(data);
          然后再去获取线程中的数据
         */
        for (int i = 0; i< 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName()
                            + "数据:" + data);
                    x.set(data);//当前线程
//                    创建对象赋值
//                    MyThreadScopeData myData = new MyThreadScopeData();
//                    myData.setAge(data);
//                    myData.setName("name" + data);
//                    localData.set(myData);

                    MyThreadScopeData.getInstace().setName("name" + data);
                    MyThreadScopeData.getInstace().setAge(data);
                    //多线程获取数据
                    new A().get();
                    new B().get();
                }
            });
            thread.start();
        }
    }

    //模块获取数据
    static class A{
        public void get(){
//            int data = x.get();
//            System.out.println("A模块>>" + Thread.currentThread().getName()
//                    + "数据:" + data);
            MyThreadScopeData myData = MyThreadScopeData.getInstace();
            System.out.println("A模块>>" + Thread.currentThread().getName() +
                    "当前数据:" +myData.getName() + ":" + myData.getAge());
        }
    }

    static class B{
        public void get(){
            int data = x.get();
            System.out.println("B" + Thread.currentThread().getName()
                    + "数据:" + data);

            MyThreadScopeData myData = MyThreadScopeData.getInstace();
            System.out.println("B" + Thread.currentThread().getName() +
                    ">>" +myData.getName() + ":" + myData.getAge());
        }
    }


}

class MyThreadScopeData{

//    private static MyThreadScopeData instace = null;
    private static ThreadLocal<MyThreadScopeData> localData = new ThreadLocal<>();
    private String name;
    private int age;

    private MyThreadScopeData(){}

    /**
     * 获取本地线程对象(有就使用,没有就创建),并将值(MyThreadScopeData对象)放入到本地线程中
     */
    static /*synchronized*/ MyThreadScopeData getInstace(){
        MyThreadScopeData instace = localData.get();
        if (instace == null){
            instace = new MyThreadScopeData();
            //本地线程赋值
            localData.set(instace);
        }
        return instace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}