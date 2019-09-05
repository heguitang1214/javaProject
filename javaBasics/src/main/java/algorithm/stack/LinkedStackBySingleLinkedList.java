package algorithm.stack;

import algorithm.linkedlist.single.SingleLinkedList;

import java.io.Serializable;


/**
 * 链式栈的实现【借助单链表实现】
 *
 * @author Tang
 */
public class LinkedStackBySingleLinkedList<T> implements Stack<T>, Serializable {

    private static final long serialVersionUID = 3409158027110650450L;

    private SingleLinkedList<T> linkedList;

    public LinkedStackBySingleLinkedList() {
        linkedList = new SingleLinkedList<>();
    }


    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    /**
     * 栈顶插入(单链表尾部)
     *
     * @param data 数据
     */
    @Override
    public void push(T data) {
        linkedList.add(data);
    }

    /**
     * 获取元素,不删除
     *
     * @return 返回栈顶数据
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
//            throw new EmptyStackException("Stack empty");
        }
        return linkedList.get(0);
    }

    /**
     * 栈顶移除
     *
     * @return 元素是否移除成功
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException("Stack empty");
        }
        // 从头获取数据,数据就是先进先出
        return linkedList.remove(0);
    }


    public static void main(String[] args) {
        LinkedStackBySingleLinkedList<String> linkedList = new LinkedStackBySingleLinkedList<>();
        linkedList.push("A");
        linkedList.push("B");
        linkedList.push("C");
        for (int i = 0; i < 3; i++) {
            System.out.println("sl.pop->" + linkedList.pop());
        }
        System.out.println(linkedList.peek());
    }

}
