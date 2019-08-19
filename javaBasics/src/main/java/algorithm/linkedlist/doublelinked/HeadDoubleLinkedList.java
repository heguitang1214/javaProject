package algorithm.linkedlist.doublelinked;


import datastructure.linkedList.ILinkedList;

/**
 * 双链表的实现,带头结点(不带数据)的双链表,为了更高的效率该类包含指向尾部的指针tail
 *
 * @param <T> 数据
 */
public class HeadDoubleLinkedList<T> implements ILinkedList<T> {

    /**
     * 不带数据的头结点
     */
    private DNode<T> head;

    /**
     * 指向尾部的指针
     */
    private DNode<T> tail;

    private HeadDoubleLinkedList() {
        //初始化头结点
        this.head = this.tail = new DNode<>();
    }


    /**
     * 传入一个数组,转换成链表
     *
     * @param array 数组
     */
    public HeadDoubleLinkedList(T[] array) {
        this();
        if (array != null && array.length > 0) {
            this.head.next = new DNode<>(array[0]);
            tail = this.head.next;
            tail.prev = this.head;
            int i = 1;
            while (i < array.length) {
                tail.next = new DNode<T>(array[i++]);
                tail.next.prev = tail;
                tail = tail.next;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return head.next == null;
    }


    @Override
    public int length() {
        int length = 0;
        DNode<T> pre = head.next;
        while (pre != null) {
            length++;
            pre = pre.next;
        }
        return length;
    }


    @Override
    public T get(int index) {
        if (index >= 0) {
            int j = 0;
            DNode<T> pre = this.head.next;
            while (pre != null && j < index) {
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
        T old = null;
        if (index > 0 && data != null) {
            int j = 0;
            DNode<T> pre = this.head.next;
            //查找需要替换的位置
            while (pre != null && j < index) {
                j++;
                pre = pre.next;
            }
            if (pre != null) {
                old = pre.data;
                //替换数据
                pre.data = data;
            }
        }
        return old;
    }

    /**
     * 插入结点
     *
     * @param index 下标
     * @param data  数据
     * @return 是否成功
     */
    @Override
    public boolean add(int index, T data) {
        if (index < 0 || data == null) {
            throw new NullPointerException("index < 0 || data == null");
        }
        int j = 0;
        DNode<T> front = this.head;
        //查找要插入结点位置的前一个结点
        while (front.next != null && j < index) {
            j++;
            front = front.next;
        }

        //创建需要插入的结点,并让其前继指针指向front,后继指针指向front.next
        DNode<T> q = new DNode<>(data, front, front.next);

        //空双链表插入,需要确保front.next不为空
        if (front.next != null) {
            //更改front.next的前继指针
            front.next.prev = q;
        }
        //更改front的后继指针
        front.next = q;

        //在尾部插入时需要注意更新tail指向
        if (front == this.tail) {
            this.tail = q;
        }
        return true;
    }

    /**
     * 尾部添加
     *
     * @param data 数据
     * @return 是否添加成功
     */
    @Override
    public boolean add(T data) {
        if (data == null) {
            return false;
        }
        //创建新结点,并把其前继指针指向tail
        DNode<T> q = new DNode<>(data, tail, null);
        tail.next = q;
        //更新尾部结点
        this.tail = q;
        return true;
    }

    /**
     * 根据下标删除结点
     * 1.头删除
     * 2.中间删除
     * 3.尾部删除,更新tail指向
     *
     * @param index 下标起始值为0
     * @return 删除的数据
     */
    @Override
    public T remove(int index) {
        int size = length();
        if (index < 0 || index >= size || isEmpty()) {
            return null;
        }
        DNode<T> p = this.head;
        int j = 0;
        if (p == null) {
            return null;
        }
        // 头删除/尾删除/中间删除,查找需要删除的结点(要删除的当前结点因此i<=index)
        while (j <= index) {
            // 这里的p最终就是需要删除的数据，这里使用的是<=
            p = p.next;
            j++;
        }

        //当链表只要一个结点时,无需此步
        if (p.next != null) {
            p.next.prev = p.prev;
        }
        p.prev.next = p.next;
        //如果是尾结点
        if (p == this.tail) {
            //更新未结点的指向
            this.tail = p.prev;
        }
        return p.data;
    }

    /**
     * 根据data删除结点,无需像单向链表那样去存储要删除结点的前一个结点
     * 1.头删除
     * 2.中间删除
     * 3.尾部删除,更新tail指向
     *
     * @param data 数据
     * @return 是否成功
     */
    @Override
    public boolean removeAll(T data) {
        boolean isRemove = false;

        if (data == null || isEmpty()) {
            return false;
        }
        // 注意这里的起点,如果起点为this.head,那么情况区别如同前面的根据index的删除实现
        DNode<T> p = this.head.next;

        // 头删除/尾删除/中间删除(size>1),查找所有需要删除的结点
        while (p != null) {
            if (data.equals(p.data)) {
                if (p == this.tail) {
                    // 如果是尾结点，更新未结点的指向
                    this.tail = p.prev;
                    p.prev = null;
                    this.tail.next = null;
                } else {
                    // 如果是在中间删除,更新前继和后继指针指向
                    p.prev.next = p.next;
                    p.next.prev = p.prev;
                }
                isRemove = true;
                // 继续查找
                p = p.next;
            } else {
                p = p.next;
            }
        }
        return isRemove;
    }

    /**
     * 清空链表
     */
    @Override
    public void clear() {
        this.head.next = null;
        this.tail = this.head;
    }


    @Override
    public boolean contains(T data) {
        if (data == null) {
            return false;
        }

        DNode<T> p = this.head.next;
        while (p != null) {
            if (data.equals(p.data)) {
                return true;
            } else {
                p = p.next;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("带头循环双链表(");
        DNode<T> pre = this.head.next;
        while (pre != null) {
            sb.append(pre.data);
            pre = pre.next;
            if (pre != null) {
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }

    public static void main(String[] args) {

        String[] letters = {"A", "B", "C", "D", "Z", "E", "F"};
//        String[] letters={"A"};
        HeadDoubleLinkedList<String> list = new HeadDoubleLinkedList<>(letters);

        System.out.println("list.get(3)->" + list.get(3));
        System.out.println("list:" + list.toString());

        System.out.println("list.add(4,Y)—>" + list.add(0, "Y"));
        System.out.println("list:" + list.toString());
        System.out.println("list.add(Z)—>" + list.add("Z"));
        System.out.println("list:" + list.toString());


        System.out.println("list.contains(Z)->" + list.contains("Z"));
        System.out.println("list.set(4,P)-->" + list.set(4, "P"));
        System.out.println("list:" + list.toString());


        System.out.println("list.remove(6)-->" + list.remove(6));
        System.out.println("list.remove(Z)->"+list.removeAll("Z"));
        System.out.println("list:" + list.toString());
    }
}
