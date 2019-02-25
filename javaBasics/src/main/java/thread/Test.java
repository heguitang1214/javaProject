package thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author heguitang
 * @Date 2019/2/25 11:00
 * @Version 1.0
 * @Desc 内存屏障的实现
 */
public class Test {

    ReentrantLock lock = new ReentrantLock(true);

    private boolean success;

    public void getResult(){
        while (success == false){
            System.out.println("执行了..." + success);
            doSomethind();
        }
    }

    private void doSomethind() {
        success = true;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.getResult();
    }
}
