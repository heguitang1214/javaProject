package jvm.gc;

import java.lang.ref.*;

/**
 * Created by 11256 on 2018/9/7.
 * GC引用测试
 *
 * 对象的finalize()方法说明:
 *      对象执行了finalize(),不一定就会被GC回收,会放到一个叫F-Queue的队列中,
 *      这个F-Queue是一个低级线程去执行，它不会保证执行完毕
 *          1.如果对象清理缓慢
 *          2.清理的时候发生异常了(极端)
 *          3.cpu切换到其他线程上...
 */
public class ReferenceTest {
    public static void main(String[] args) {
        /*
        一般是通过 SoftReference 的构造方法，将需要用弱引用来指向的对象包装起来。
        当需要使用的时候，调用SoftReference的get()方法来获取。
        当对象未被回收时SoftReference的get()方法会返回该对象的强引用。
         */
        System.out.println("===========================[软引用]=====================================");
        softReferenceTest();//软引用
        System.out.println("===========================[弱引用]=====================================");
        weakReferenceTest();//弱引用
        System.out.println("===========================[虚引用1]=====================================");
        /*
        虚引用主要用于跟踪对象被垃圾回收的状态，虚引用不能单独使用，
        虚引用必须和引用队列（ReferenceQueue）联合使用。
        程序可以通过检查与虚引用关联的引用队列中是否已经包含了该虚引用，从而了解虚引用所引用对象是否即将被回收。
         */
        phantomReferenceTest1();//幽灵引用_1
        System.out.println("===========================[虚引用2]=====================================");
        phantomReferenceTest2();//幽灵引用_2
    }

    /**
     * 软引用
     */
    private static void softReferenceTest() {
        //在堆中创建一个对象Person,在栈中创建一个p来强引用此对象Obj
        Person p = new Person(1);
        //在栈中创建一个softReference来软引用此对象Obj 可以获取对象的属性值
        SoftReference<Person> softReference = new SoftReference<>(p);
        System.out.println("强引用获取:" + p.getId());//输出打印:1
        Person person = softReference.get();
        printdesc(softReference, "存在强引用", "软引用");
        //断开p和Person的强引用
        p = null;
//        System.out.println(p.getId());  //NullPointerException
        System.gc();
        //虽然断开了p和Obj的强引用,但是并没有被回收,如果在前面调用gc()垃圾回收,运行结果也是打印1的.
        // 软引用只有系统在发生内存溢出异常之前，会把只被软引用的对象进行回收
        printdesc(softReference, "强引用置空", "软引用");
    }

    /**
     * 弱引用
     */
    private static void weakReferenceTest() {
        //在堆中创建一个对象Person,在栈中创建一个p来强引用此对象Person
        Person p = new Person(1);
        //在栈中创建一个weakReference来弱引用此对象Person 可以获取对象的属性值
        WeakReference<Person> weakReference = new WeakReference<>(p);

        printdesc(weakReference, "存在强引用", "弱引用");
        //断开p和Person的强引用
        p = null;
        System.gc();

        //p=null之后,还是可以正常的打印输出1,这说明断开强引用和其他弱引用,软引用压根没有关系.
        //如果在打印之前 调用gc() 方法之后  就会报错..java.lang.NullPointerException
        //垃圾回收不论内存是否不足都会回收只被弱引用关联的对象。
        printdesc(weakReference, "强引用被置空,不管GC有没有发生", "弱引用");

    }

    /**
     * 虚(幽灵)引用1
     */
    private static void phantomReferenceTest1() {
        //在堆中创建一个对象Person,在栈中创建一个p来强引用此对象Person
        Person p = new Person(1);

        //Phantom 幻影幽灵 的意思
        ReferenceQueue<Person> referenceQueue = new ReferenceQueue<>();
        //在栈中创建一个phantomReference来虚引用此对象Obj 不可以获取对象的属性值
        PhantomReference<Person> phantomReference = new PhantomReference<>(p, referenceQueue);

        // PhantomReference的唯一作用就是能在这个对象被收集器回收时收到一个系统通知
        // 具体查看phantomReferenceTest2()方法
        printdesc(phantomReference, "强引用未手动置空", "虚引用");

    }

    /**
     * 虚引用,返回系统通知
     */
    private static void phantomReferenceTest2() {
        //在堆中创建一个对象Person,在栈中创建一个p来强引用此对象Person
        Person p = new Person(1);

        //Phantom 幻影幽灵 的意思
        ReferenceQueue<Person> referenceQueue = new ReferenceQueue<>();
        //在栈中创建一个phantomReference来虚引用此对象Obj 不可以获取对象的属性值
        PhantomReference<Person> phantomReference = new PhantomReference<>(p, referenceQueue);
        printdesc(phantomReference, "存在强引用", "虚引用");
        System.out.println("获取referenceQueue poll()队列中的元素:" + referenceQueue.poll());//打印输出: null 这个是查询队列中是否有元素.

        //断开p和Person的强引用
        p = null;
        //p被回收之后,队列referenceQueue中就有值了,给系统通知.
        System.gc();
        System.out.println("GC后直接获取referenceQueue poll()队列中的元素:" + referenceQueue.poll());
        //过一秒钟之后再查询队列中是否有元素.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //PhantomReference的唯一作用就是能在这个对象被收集器回收时收到一个系统通知,如果这个对象被回收了,
        // 就会把通知放到队列中.回收的标志就是把通知放到队列中
        //如果前面p=null注释掉,再运行打印输出就是null,因为p没有被回收(强引用中),就不会把通知放到队列中,队列中为空 null
        System.out.println("过一秒获取referenceQueue poll()队列中的元素:" + referenceQueue.poll());
        //可能性输出:打印输出: java.lang.ref.PhantomReference@4dc63996(对象不能重写final())

    }

    /**
     * 打印输出
     * @param reference 引用类型
     * @param desc 前置描述
     * @param desc1 后置描述
     */
    private static void printdesc(Reference<Person> reference, String desc, String desc1){
        Person person = reference.get();
        if (person != null) {
            System.out.println(""+ desc+"," + desc1 + "获取:" + person.getId());
        }else {
            System.out.println(""+ desc+"," + desc1 + "获不到对应的信息...");
        }
    }



    static class Person {
        Person(Integer id) {
            this.id = id;
        }
        private Integer id;
        Integer getId() {
            return id;
        }

//        @Override
//        protected void finalize() throws Throwable {
//            System.out.println("GC finalized......");
//            super.finalize();
//        }
    }

}

