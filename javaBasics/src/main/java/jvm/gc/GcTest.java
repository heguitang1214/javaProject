package jvm.gc;

/**
 * Created by 11256 on 2018/9/9.
 *  测试JVM的各种垃圾回收器,不同的JDK版本支持的组合可能不一样.
 *
 *   uint i = 0;
 *   if (UseSerialGC)                       i++;
 *   if (UseConcMarkSweepGC || UseParNewGC) i++;
 *   if (UseParallelGC || UseParallelOldGC) i++;
 *   if (UseG1GC)                           i++;
 *   if (i > 1) {
 *     jio_fprintf(defaultStream::error_stream(),
 *                 "Conflicting collector combinations in option list; "
 *                 "please refer to the release notes for the combinations "
 *                 "allowed\n");
 *     status = false;
 *   }
 */
public class GcTest {

    public static void main(String[] args) {
//        defaultTest();
        //新生代是ParallelGC,也就是默认的形式
        parallelGC_ParallelOldGC();

        //单个收集器的使用
//        serialGC();

//        concMarkSweepGC();

        //新生代收集器是parNewGC
//        parNewGC_ConcMarkSweepGC();
//        parNewGC_SerialOldGC();//失败
//        parNewGC_ParallelOldGC();//失败

        //新生代收集器是serialGC
//        serialGC_CMSGC();//失败
//        serialGC_SerialOldGC();//失败



    }


    /**
     * -XX:+PrintGCDetails
     * 1.8默认的垃圾回收器Parallel Scavenge + Parallel Old
     */
    private static void defaultTest(){
        System.out.println("默认的垃圾回收....");
//        Heap
//        PSYoungGen      total 75776K, used 6538K [0x000000076bb80000, 0x0000000771000000, 0x00000007c0000000)
//        eden space 65024K, 10% used [0x000000076bb80000,0x000000076c1e2ae8,0x000000076fb00000)
//        from space 10752K, 0% used [0x0000000770580000,0x0000000770580000,0x0000000771000000)
//        to   space 10752K, 0% used [0x000000076fb00000,0x000000076fb00000,0x0000000770580000)
//        ParOldGen       total 173568K, used 0K [0x00000006c3200000, 0x00000006cdb80000, 0x000000076bb80000)
//        object space 1 73568K, 0% used [0x00000006c3200000,0x00000006c3200000,0x00000006cdb80000)
//        Metaspace       used 3433K, capacity 4496K, committed 4864K, reserved 1056768K
//        class space    used 374K, capacity 388K, committed 512K, reserved 1048576K
    }

    /**
     *  -XX:+PrintGCDetails -XX:+UseParallelGC -XX:+UseParallelOldGC(默认)
     */
    private static void parallelGC_ParallelOldGC(){
        System.out.println("默认:新生代[UseParallelGC],老年代[UseParallelOldGC]组合....");
//        Heap
//        PSYoungGen      total 38400K, used 3997K [0x00000000d5d80000, 0x00000000d8800000, 0x0000000100000000)
//        eden space 33280K, 12% used [0x00000000d5d80000,0x00000000d6167500,0x00000000d7e00000)
//        from space 5120K, 0% used [0x00000000d8300000,0x00000000d8300000,0x00000000d8800000)
//        to   space 5120K, 0% used [0x00000000d7e00000,0x00000000d7e00000,0x00000000d8300000)
//        ParOldGen       total 87552K, used 0K [0x0000000081800000, 0x0000000086d80000, 0x00000000d5d80000)
//        object space 87552K, 0% used [0x0000000081800000,0x0000000081800000,0x0000000086d80000)
//        Metaspace       used 3264K, capacity 4500K, committed 4864K, reserved 1056768K
//        class space    used 355K, capacity 388K, committed 512K, reserved 1048576K
    }



    /**
     * -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseConcMarkSweepGC与参数-XX:+UseConcMarkSweepGC一样
     * 不支持
     * -XX:+UseParNewGC -XX:+UseParallelOldGC
     * -XX:+UseParNewGC -XX:+UseSerialGC
     */
    private static void parNewGC_ConcMarkSweepGC(){
        System.out.println("新生代[UseParNewGC],老年代[UseConcMarkSweepGC]组合....");
//        Heap
//        par new generation   total 78016K, used 6942K [0x00000006c3200000, 0x00000006c86a0000, 0x00000006f7200000)
//        eden space 69376K,  10% used [0x00000006c3200000, 0x00000006c38c7818, 0x00000006c75c0000)
//        from space 8640K,   0% used [0x00000006c75c0000, 0x00000006c75c0000, 0x00000006c7e30000)
//        to   space 8640K,   0% used [0x00000006c7e30000, 0x00000006c7e30000, 0x00000006c86a0000)
//        concurrent mark-sweep generation total 173440K, used 0K [0x00000006f7200000, 0x0000000701b60000, 0x00000007c0000000)
//        Metaspace       used 3429K, capacity 4496K, committed 4864K, reserved 1056768K
//        class space    used 373K, capacity 388K, committed 512K, reserved 1048576K
    }

    /**
     *  -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseSerialOldGC
     */
    private static void parNewGC_SerialOldGC(){
        System.out.println("新生代[UseParNewGC],老年代[UseSerialOldGC]组合....");
        //
    }

    /**
     *  -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseParallelOldGC
     */
    private static void parNewGC_ParallelOldGC(){
        System.out.println("新生代[UseParNewGC],老年代[UseParallelOldGC]组合....");
        //
    }

    /**
     *  -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+UseConcMarkSweepGC
     */
    private static void serialGC_CMSGC(){
        System.out.println("新生代[SerialGC],老年代[UseConcMarkSweepGC]组合....");
//
    }

    /**
     *  -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+UseSerialOldGC
     */
    private static void serialGC_SerialOldGC(){
        System.out.println("新生代[SerialGC],老年代[UseSerialOldGC]组合....");
        //
    }


    //=============================================单个GC配置===============================================
    /**
     * -XX:+PrintGCDetails -XX:+UseSerialGC
     */
    private static void serialGC() {
        System.out.println("使用垃圾回收器[SerialGC]......");
//        Heap
//        def new generation   total 78016K, used 5554K [0x00000006c3200000, 0x00000006c86a0000, 0x00000007176a0000)
//        eden space 69376K,   8% used [0x00000006c3200000, 0x00000006c376c9f0, 0x00000006c75c0000)
//        from space 8640K,   0% used [0x00000006c75c0000, 0x00000006c75c0000, 0x00000006c7e30000)
//        to   space 8640K,   0% used [0x00000006c7e30000, 0x00000006c7e30000, 0x00000006c86a0000)
//        tenured generation   total 173440K, used 0K [0x00000007176a0000, 0x0000000722000000, 0x00000007c0000000)
//        the space 173440K,   0% used [0x00000007176a0000, 0x00000007176a0000, 0x00000007176a0200, 0x0000000722000000)
//        Metaspace       used 3366K, capacity 4500K, committed 4864K, reserved 1056768K
//        class space    used 369K, capacity 388K, committed 512K, reserved 1048576K
    }


    /**
     * -XX:+PrintGCDetails -XX:+UseParNewGC
     */
    private static void parNewGC() {
        System.out.println("使用垃圾回收器[ParallelOldGC]......");
        //
    }

    /**
     * -XX:+PrintGCDetails -XX:+UseParallelGC
     */
    private static void parallelGC() {
        System.out.println("使用垃圾回收器[ParallelOldGC]......");
        //
    }

    /**
     * -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC
     */
    private static void concMarkSweepGC() {
        System.out.println("使用垃圾回收器[ParallelOldGC]......");
        //
    }

    /**
     * -XX:+PrintGCDetails -XX:+UseParallelOldGC
     */
    private static void parallelOldGC() {
        System.out.println("使用垃圾回收器[ParallelOldGC]......");
        //
    }


    //-XX:+PrintGCDetails -XX:+UseSerialGC              def new generation + tenured generation
    //-XX:+PrintGCDetails -XX:+UseParNewGC              par new generation + tenured generation
    //-XX:+PrintGCDetails -XX:+UseParallelGC            PSYoungGen + ParOldGen
    //-XX:+PrintGCDetails -XX:+UseConcMarkSweepGC       par new generation + concurrent mark-sweep
    //-XX:+PrintGCDetails -XX:+UseParallelOldGC         PSYoungGen + ParOldGen

}






