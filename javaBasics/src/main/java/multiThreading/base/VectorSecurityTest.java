package multiThreading.base;

import java.util.Vector;

/**
 * Created by 11256 on 2018/8/19.
 * Vector多线程安全测试
 */
public class VectorSecurityTest {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {

        while(true){
            for (int i = 0; i<10; i++){
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++){
                        vector.remove(i);
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++){
                        System.out.println(vector.get(i));
                    }
                }
            });

            removeThread.start();
            printThread.start();

            //不要同时产生过多的线程,否则会导致系统假死
            while (Thread.activeCount() > 500);

        }


    }


}
