package thread2.t07_synchronizedemo.part2;

public class Test {
    public void t() {
        System.out.println("t()....");
        try {
            wait(10);
        } catch (InterruptedException e) {
            System.out.println("ex");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Test t = new Test();
        t.t();
    }
}
