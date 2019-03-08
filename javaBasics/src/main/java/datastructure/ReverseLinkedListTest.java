package datastructure;

/**
 * @Author heguitang
 * @Date 2019/3/8 16:48
 * @Version 1.0
 * @Desc
 */
public class ReverseLinkedListTest {

    private static Node head;

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private static void reverseLinkedList() {
        if (head == null || head.next == null) {
            return;
        }
        Node p1 = head;
        Node p2 = head.next;
        Node p3;
        while (p2 != null) {
            p3 = p2.next;
            //逆序
            p2.next = p1;
            p1 = p2;
            p2 = p3;
        }
        head.next = null;
        head = p1;
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

        //逆序链表
        reverseLinkedList();
        System.out.println();
        //逆序后输出链表
        temp = head;
        while (temp != null) {
            System.out.print(temp.data + ",");
            temp = temp.next;
        }

    }


}
