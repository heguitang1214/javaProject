package jvm.jdkTools;

/**
 * Created by 11256 on 2018/9/11.
 * Java工具使用测试
 */
public class JavaUtilTest {
    private static final int _MB = 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始====================================");
        jps_jstat_test();
        System.out.printf("jps/jstat测试结束=========================");
    }

    /**
     * 分析GC回收情况
     * jps
     * jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]
     * 使用示例：jstat -gc jps进程号 1000 20
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:+UseParNewGC -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=6 -XX:+PrintTenuringDistribution
     */
    private static void jps_jstat_test() throws InterruptedException {
        Thread.sleep(1000 * 30);
        //两次垃圾回收，都是因为内存空间不够
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_MB / 4];
        allocation2 = new byte[4 * _MB];
        allocation3 = new byte[4 * _MB];
        allocation3 = null;
        allocation3 = new byte[4 * _MB];
    }




}
