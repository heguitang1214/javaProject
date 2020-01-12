package thread2.t16_arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-11-25 22:29
 * @Description: ArrayBlockingQueue使用方式
 */
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(5);
        System.out.println("增加值之前" + arrayBlockingQueue.size());
        for (int i = 0; i < 5; i++) {
            arrayBlockingQueue.add(i + "");
        }
        System.out.println("增加值之后" + arrayBlockingQueue.size());
        System.out.println(arrayBlockingQueue.toString());

        System.out.println("取值开始：");
        for (int i = 0; i < 5; i++) {
            System.out.println("取出的值为：" + arrayBlockingQueue.poll());
        }
    }
}
