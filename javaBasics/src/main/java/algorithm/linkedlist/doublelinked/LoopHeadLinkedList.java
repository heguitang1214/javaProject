package algorithm.linkedlist.doublelinked;


import algorithm.linkedlist.ILinkedList;

/**
 * 循环双链表,带空头结点(不含数据),循环链表可以不需要尾部指针
 */
public class LoopHeadLinkedList<T> implements ILinkedList<T> {

    /**
     * 不带数据的头结点
     */
    DNode<T> head;
    //   protected DNode<T> tail; //指向尾部的指针

    LoopHeadLinkedList() {
        //初始化头结点
        this.head = new DNode<>();
        this.head.next = head;
        this.head.prev = head;
    }


    /**
     * 传入一个数组,转换成链表
     *
     * @param array 数组
     */
    public LoopHeadLinkedList(T[] array) {
        this();
        if (array != null && array.length > 0) {
            DNode<T> p = new DNode<>(array[0]);
            head.next = p;
            // 头指向尾节点
            head.prev = p;

            p.prev = head;
            p.next = head;

            int i = 1;
            while (i < array.length) {
                // 后++
                p.next = new DNode<>(array[i++], p, head);
                // 头结点指向尾节点，形成循环列表
                head.prev = p.next;
                p = p.next;
            }
        }
    }


    @Override
    public boolean isEmpty() {
        //循环双链表的后继指针指向自己说明是空链表
        return this.head.next == head;
    }

    @Override
    public int length() {
        int length = 0;
        DNode<T> p = this.head.next;
        while (p != this.head) {
            length++;
            p = p.next;
        }
        return length;
    }

    @Override
    public T get(int index) {
        if (index >= 0) {
            int j = 0;
            DNode<T> p = this.head.next;
            while (p != head && j < index) {
                j++;
                p = p.next;
            }
            if (p != head) {
                return p.data;
            }
        }
        return null;
    }

    /**
     * 根据index修改data
     *
     * @param index 链表下标
     * @param data  数据
     * @return 返回被替换的data
     */
    @Override
    public T set(int index, T data) {
        if (data == null) {
            return null;
        }

        if (index >= 0) {
            int j = 0;
            DNode<T> p = this.head.next;

            while (p != head && j < index) {
                j++;
                p = p.next;
            }

            // 如果不是头结点
            if (p != head) {
                T old = p.data;
                p.data = data;
                return old;
            }
        }
        return null;
    }

    /**
     * 根据index添加
     * 循环链表中无论是prev还是next都不存在空的情况,因此添加时
     * 无论是头部还是尾部还是中,都视为一种情况对待
     *
     * @param index 下标
     * @param data  数据
     * @return 是否添加成功
     */
    @Override
    public boolean add(int index, T data) {
        int size = length();
        if (data == null || index < 0 || index >= size) {
            return false;
        }
        int j = 0;
        DNode<T> p = this.head;
        // 寻找插入点的位置
        while (p.next != head && j < index) {
            p = p.next;
            j++;
        }

        // 创建新结点,如果index=3,那么插入的位置就是第4个位置
        DNode<T> q = new DNode<>(data, p, p.next);
        p.next = q;
        p.next.prev = q;

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
        // 创建新结点,让前继指针指向head.pre,即：head.pre=尾节点；后继指针指向head
        DNode<T> p = new DNode<>(data, head.prev, head);
        // 更新tail后继指针的指向
        this.head.prev.next = p;
        this.head.prev = p;

        return true;
    }

    @Override
    public T remove(int index) {
        T old = null;
        int size = length();

        if (index < 0 || index >= size) {
            return null;
        }
        int j = 0;
        DNode<T> p = this.head.next;

        while (p != head && j < index) {
            j++;
            p = p.next;
        }

        if (p != head) {
            old = p.data;
            p.prev.next = p.next;
            p.next.prev = p.prev;
        }
        return old;
    }

    @Override
    // TODO: 2019/8/19 删除数据出错
    public boolean removeAll(T data) {
        boolean isRemove = false;

        if (data == null) {
            return false;
        }

        DNode<T> p = this.head.next;
        while (p != head) {
            if (data.equals(p.data)) {
                p.prev.next = p.next;
                p.next.prev = p.prev;
                isRemove = true;
            }
            p = p.next;
        }
        return isRemove;
    }

    @Override
    public void clear() {
        this.head.prev = head;
        this.head.next = head;
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            return false;
        }

        DNode<T> p = this.head.next;
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
        StringBuilder sb = new StringBuilder("循环双链表(");
        DNode<T> p = this.head.next;
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

        String[] letters = {"A", "B", "C", "D", "Z", "E", "F"};
        LoopHeadLinkedList<String> list = new LoopHeadLinkedList<>(letters);

        System.out.println("init list-->" + list.toString());

        System.out.println("list.get(3)->" + list.get(3));
        System.out.println("list:" + list.toString());

        System.out.println("list.add(4,Y)—>" + list.add(4, "Y"));
        System.out.println("list:" + list.toString());
        System.out.println("list.add(Z)—>" + list.add("Z"));
        System.out.println("list:" + list.toString());


        System.out.println("list.contains(Z)->" + list.contains("Z"));
        System.out.println("list.set(4,P)-->" + list.set(4, "P"));
        System.out.println("list:" + list.toString());


        System.out.println("list.remove(3)-->" + list.remove(3));
        System.out.println("list.removeAll(Z)->" + list.removeAll("Z"));
        System.out.println("list:" + list.toString());
    }
}
