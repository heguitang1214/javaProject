package algorithm.linkedlist.single;


import algorithm.linkedlist.ILinkedList;

/**
 * 循环单链表
 *
 * @author Tang
 */
public class CircularHeadLinkedList<T> implements ILinkedList<T> {

    /**
     * 不带数据头结点
     */
    private Node<T> head;

    /**
     * 指向尾部的指针
     */
    private Node<T> tail;


    public CircularHeadLinkedList() {
        //初始化头结点与尾指针
        this.head = new Node<>(null);
        this.head.next = head;
        this.tail = head;
    }

    /**
     * 将数组转换成循环单链表
     *
     * @param array 数组
     */
    public CircularHeadLinkedList(T[] array) {
        this();
        if (array != null && array.length > 0) {
            this.head.next = new Node<>(array[0], head);
            tail = this.head.next;
            int i = 1;
            while (i < array.length) {
                tail.next = new Node<>(array[i++]);
                tail.next.next = head;
                tail = tail.next;
            }
        }
    }


    @Override
    public boolean isEmpty() {
        return this.head.next == head;
    }

    @Override
    public int length() {
        int length = 0;
        Node<T> p = this.head.next;
        while (p != head) {
            p = p.next;
            length++;
        }
        return length;
    }

    @Override
    public T get(int index) {
        if (index >= 0) {
            int j = 0;
            Node<T> pre = this.head.next;
            // 当pre是空头结点的时候，对应的Node<T>不为null
            // 如果是pre != null，则为循环取值，index 大于链表size也能拿到值
            while (pre != head && j < index) {
                j++;
                pre = pre.next;
            }
            if (pre != null) {
                return pre.data;
            }
        }
        return null;
    }

    @Override
    public T set(int index, T data) {
        if (data == null) {
            return null;
        }

        if (index >= 0) {
            int j = 0;
            Node<T> p = this.head.next;

            while (p != head && j < index) {
                j++;
                p = p.next;
            }
            //如果不是头结点，那就是替换数据，否则，就是尾部插入数据
            if (p != head) {
                T old = p.data;
                p.data = data;
                return old;
            }
        }
        return null;
    }


    @Override
    public boolean add(int index, T data) {
        int size = length();
        if (data == null || index < 0 || index >= size) {
            return false;
        }

        int j = 0;
        Node<T> p = this.head;
        // 寻找插入点的位置的前一个结点，假设index=3，那么找的下标就是2，也就是前一个节点
        while (p.next != head && j < index) {
            p = p.next;
            j++;
        }

        // 创建新结点,如果index=3,那么插入的位置就是第4个位置
        // 创建一个新节点q，新节点q的下一个节点指向p.next
        Node<T> q = new Node<>(data, p.next);
        p.next = q;
        // 更新尾部指向
        if (p == tail) {
            this.tail = q;
        }
        return true;
    }


    @Override
    public boolean add(T data) {
        if (data == null) {
            return false;
        }
        Node<T> q = new Node<>(data, this.tail.next);
        this.tail.next = q;
        // 更新尾部指向
        this.tail = q;
        return true;
    }


    @Override
    public T remove(int index) {
        int size = length();
        if (index < 0 || index >= size || isEmpty()) {
            return null;
        }

        int j = 0;
        Node<T> p = this.head.next;

        // 找到当前需要删除的节点的前一个节点
        while (p != head && j < index) {
            j++;
            p = p.next;
        }

        // 首先获取的节点就是头结点的下一个节点，如果p == head，说明是一个空链表
        if (p != head) {
            T old = p.next.data;
            if (tail == p.next) {
                tail = p;
            }
            p.next = p.next.next;
            return old;
        }
        return null;
    }

    @Override
    public boolean removeAll(T data) {
        boolean isRemove = false;
        if (data == null) {
            return false;
        }
        //用于记录要删除结点的前一个结点
        Node<T> front = this.head;
        //当前遍历的结点
        Node<T> pre = front.next;
        //查找所有数据相同的结点并删除
        while (pre != head) {
            if (data.equals(pre.data)) {
                //如果恰好是尾部结点,则更新rear的指向
                if (data.equals(tail.data)) {
                    this.tail = front;
                }
                //相等则删除pre并更改指针指向
                front.next = pre.next;
                pre = front.next;
                isRemove = true;
            } else {
                front = pre;
                pre = pre.next;
            }
        }
        return isRemove;
    }

    @Override
    public void clear() {
        this.head.next = head;
        this.tail = head;
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            return false;
        }

        Node<T> p = this.head.next;
        while (p != head) {
            if (data.equals(p.data)) {
                return true;
            }
            p = p.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("循环单链表(");
        Node<T> p = this.head.next;
        while (p != head) {
            sb.append(p.data.toString());
            p = p.next;
            if (p != head) {
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }

    public static void main(String[] args) {

        String[] letters = {"A", "B", "C", "D", "E", "F"};
        CircularHeadLinkedList<String> list = new CircularHeadLinkedList<>(letters);

        System.out.println("list.get(3)->" + list.get(3));
        System.out.println("list:" + list.toString());

        System.out.println("list.add(4,Y)—>" + list.add(4, "Y"));
        System.out.println("list:" + list.toString());
        System.out.println("list.add(Z)—>" + list.add("Z"));
        System.out.println("list:" + list.toString());


        System.out.println("list.contains(Z)->" + list.contains("Z"));
        System.out.println("list.set(4,P)-->" + list.set(4, "P"));
        System.out.println("list:" + list.toString());

        System.out.println("list.removeAll(Z)->" + list.removeAll("Z"));
        System.out.println("list.remove(4)-->" + list.remove(4));
        System.out.println("list:" + list.toString());
    }
}
