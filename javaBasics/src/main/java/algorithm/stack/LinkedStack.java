package algorithm.stack;

import algorithm.linkedlist.single.Node;

import java.io.Serializable;

/**
 * 基于链表的栈实现
 */
public class LinkedStack<T> implements Stack<T>, Serializable {

    private static final long serialVersionUID = 1911829302658328353L;

    /**
     * 栈顶元素
     */
    private Node<T> top;

    /**
     * 栈大小
     */
    private int size;

    public LinkedStack() {
        this.top = new Node<>();
    }

    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return top == null || top.data == null;
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new StackException("data can\'t be null");
        }
        if (this.top == null) {
            this.top = new Node<>(data);
        } else if (this.top.data == null) {
            this.top.data = data;
        } else {
            Node<T> p = new Node<>(data, this.top);
            //更新栈顶
            top = p;
        }
        size++;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
//            throw new EmptyStackException("Stack empty");
        }
        return top.data;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException("Stack empty");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }


    public static void main(String[] args) {
        LinkedStack<String> sl = new LinkedStack<>();
        sl.push("A");
        sl.push("B");
        sl.push("C");
        int length = sl.size();
        for (int i = 0; i < length; i++) {
            System.out.println("sl.pop->" + sl.pop());
        }
    }
}
