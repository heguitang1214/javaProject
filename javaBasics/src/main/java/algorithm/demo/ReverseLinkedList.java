package algorithm.demo;

/**
 * 链表逆序
 *
 * @author Tang
 */
public class ReverseLinkedList {

    /**
     * 头结点
     */
    private static Node head;

    /**
     * 链表逆序
     */
    private static void reverseLinkedList() {
        if (head == null || head.next == null) {
            return;
        }

        Node p1 = head;
        Node p2 = head.next;
        Node p3 = null;

        while (p2 != null) {
            p3 = p2.next;
            // p2结点逆序
            p2.next = p1;

            // p1，p2结点后移
            p1 = p2;
            p2 = p3;
        }

        // 逆序完成后，重新赋值头结点和尾节点
        head.next = null;
        head = p1;
    }


    /**
     * 链表节点
     */
    private static class Node {
        private int data;
        private Node next;

        Node(int data) {
            this.data = data;
        }
    }


    public static void main(String[] args) {
        //初始化链表
        head = new Node(3);
        head.next = new Node(5);
        Node temp = head.next;
        temp.next = new Node(1);
        temp = temp.next;
        temp.next = new Node(4);
        temp = temp.next;
        temp.next = new Node(9);

        //逆序前输出链表
        temp = head;
        while (temp != null) {
            System.out.print(temp.data + ",");
            temp = temp.next;
        }
        System.out.println();

        System.out.println("=================逆序链表=====================");
        //逆序链表
        reverseLinkedList();

        //逆序后输出链表
        temp = head;
        while (temp != null) {
            System.out.print(temp.data + ",");
            temp = temp.next;
        }
    }

}
