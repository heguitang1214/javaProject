package jvm.gc;

import java.nio.ByteBuffer;

/**
 * Created by heguitang on 2018/11/4.
 * JVM调优
 */
public class JVMTuning {

    public static void main(String[] args) {
        byteBufferTest();
    }

    /**
     * 直接内存测试
     * -Xms50m -Xmx50m -XX:+PrintGCDetails -XX:+DisableExplicitGC
     */
    private static void byteBufferTest(){
        while (true){
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
        }
    }


}
