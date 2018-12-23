package multiThreading.singleThread;

public class Producer {

    private Depot depot;
    public Producer(Depot depot) {
        this.depot = depot;
    }

    public void produce(int no) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                depot.produce(no);
            }
        }, no + " produce thread").start();
    }
}
