package algorithm.demo;

import java.util.Stack;

/**
 * 如何用栈实现队列
 *
 * @author heguitang
 */
public class StackQueue {

    private Stack<Integer> stackA = new Stack<>();
    private Stack<Integer> stackB = new Stack<>();

    /**
     * 入栈操作
     *
     * @param element 入栈元素
     */
    private void enQueue(int element) {
        stackA.push(element);
    }

    /**
     * 出栈操作
     *
     * @return 出栈元素
     */
    private Integer deQueue() {
        if (stackB.isEmpty()) {
            if (stackA.isEmpty()) {
                return null;
            }
            transfer();
        }
        return stackB.pop();
    }

    /**
     * 栈A中的元素转移到栈B
     */
    private void transfer() {
        while (!stackA.isEmpty()) {
            stackB.push(stackA.pop());
        }
    }


    public static void main(String[] args) {
        StackQueue stackQueue = new StackQueue();
        stackQueue.enQueue(1);
        stackQueue.enQueue(2);
        stackQueue.enQueue(3);
        System.out.println(stackQueue.deQueue());
        System.out.println(stackQueue.deQueue());
        stackQueue.enQueue(4);
        System.out.println(stackQueue.deQueue());
        System.out.println(stackQueue.deQueue());

    }

}
