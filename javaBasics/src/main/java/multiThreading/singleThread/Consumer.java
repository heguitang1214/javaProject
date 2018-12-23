package multiThreading.singleThread;

public class Consumer {

    private Depot depot;
    public Consumer(Depot depot) {
        this.depot = depot;
    }

    public void consume(int no) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                depot.consume(no);
            }
        }, no + " consume thread").start();
    }

}
