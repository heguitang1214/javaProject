package multiThreading.base.deadlock;

/**
 * @author he_guitang
 *  死锁
 */
public class Deadlock implements Runnable{

    private boolean flag;

    public Deadlock(boolean flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag){
            //需要同时具有A,B两把锁
            while (true){
                synchronized (MyLock.locka){
                    System.out.println("if....A锁....");
                    synchronized (MyLock.lockb){
                        System.out.println("if....B锁....");
                    }
                }
            }
        }else {
            //需要同时具有A,B两把锁
            while (true){
                synchronized (MyLock.lockb){
                    System.out.println("else...B锁...");
                    synchronized (MyLock.locka){
                        System.out.println("else...A锁...");
                    }
                }
            }
        }
    }

    static class MyLock{

        static final Object locka = new Object();
        static final Object lockb = new Object();

    }

}




