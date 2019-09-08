package algorithm.demo.stack;

import java.util.Stack;

/**
 * 最小栈的实现：实现一个栈，该栈的出栈、入栈、取最小元素三个方法的时间复杂度为O(1)
 *
 * @author heguitang
 */
public class MinStack {

    private Stack<Integer> mainStack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    /**
     * 入栈操作
     *
     * @param element 入栈元素
     */
    public void push(int element) {
        mainStack.push(element);
        //如果辅助栈为空，或新元素小于等于辅助栈栈顶，则新元素压入辅助栈
        if (mainStack.empty() || element <= minStack.peek()) {
            minStack.push(element);
        }
    }

    /**
     * 出栈操作
     */
    public Integer pop() {
        //如果出栈元素和辅助栈栈顶元素值相等，辅助栈出栈
        if (mainStack.peek().equals(minStack.peek())) {
            minStack.pop();
        }
        return mainStack.pop();
    }


    /**
     * 获取栈的最小元素
     */
    private int getMin() throws Exception {
        if (mainStack.empty()) {
            throw new Exception("stack is empty");
        }

        return minStack.peek();
    }

    public static void main(String[] args) throws Exception {
        MinStack stack = new MinStack();
        stack.push(4);
        stack.push(9);
        stack.push(7);
        stack.push(3);
        stack.push(8);
        stack.push(5);
        System.out.println(stack.getMin());

        stack.pop();
        stack.pop();
        stack.pop();
        System.out.println(stack.getMin());
    }


}
