package algorithm.linkedlist.single;


import algorithm.linkedlist.ILinkedList;


/**
 * 带头结点并含有尾指针的链表
 *
 * @param <T> 泛型
 * @author Tang
 */
public class HeadSingleLinkedList<T> implements ILinkedList<T> {

    /**
     * 不带数据头结点
     */
    private Node<T> headNode;

    /**
     * 指向尾部的指针，链表最后的一个数据节点
     */
    private Node<T> rear;

    public HeadSingleLinkedList() {
        //初始化头结点与尾指针
        this.headNode = rear = new Node<>(null);
    }

    public HeadSingleLinkedList(Node<T> head) {
        this();
        this.headNode.next = rear.next = head;
        //更新末尾指针指向
        rear = rear.next;
    }

    /**
     * 传入一个数组,转换成链表
     *
     * @param array 数组
     */
    public HeadSingleLinkedList(T[] array) {
        this();
        if (array != null && array.length > 0) {
            this.headNode.next = new Node<>(array[0]);
            rear = this.headNode.next;
            int i = 1;
            while (i < array.length) {
                // i++ 先运算，再赋值
                rear.next = new Node<>(array[i++]);
                rear = rear.next;
            }
        }
    }

    /**
     * 通过传入的链表构造新的链表
     *
     * @param list 链表
     */
    public HeadSingleLinkedList(HeadSingleLinkedList<T> list) {
        this();
        if (list != null && list.headNode.next != null) {
            Node<T> p = list.headNode.next;
//            this.headNode.next = p;
//            rear = p;
            while (p != null) {
                rear.next = new Node<>(p.data);
                rear = rear.next;
                // 如果加上注释的两句代码，就会出现死循环
                // 问题出现在rear.next = new Node<>(p.data); 和 p = p.next;上
                // 尾指针指向节点p，然后创建的新节点还是使用的p的数据(p1)，其实也就是节点p，
                // 所以，在p.next就是指向自己p(p1)，即：单个节点的无限循环
                p = p.next;
            }
        }
    }


    /**
     * 判断链表是否为空
     *
     * @return 链表是否为空
     */
    @Override
    public boolean isEmpty() {
        return this.headNode.next == null;
    }

    @Override
    public int length() {
        int length = 0;
        Node<T> currentNode = headNode.next;
        while (currentNode != null) {
            length++;
            currentNode = currentNode.next;
        }
        return length;
    }

    /**
     * 根据index索引获取值
     *
     * @param index 下标值起始值为0
     * @return 索引对应的数据
     */
    @Override
    public T get(int index) {
        if (index >= 0) {
            int j = 0;
            Node<T> pre = this.headNode.next;
            //找到对应索引的结点
            while (pre != null && j < index) {
                pre = pre.next;
                j++;
            }

            if (pre != null) {
                return pre.data;
            }
        }
        return null;
    }

    /**
     * 根据索引替换对应结点的data
     *
     * @param index 下标从0开始
     * @param data  需要替换的数据
     * @return 返回旧值
     */
    @Override
    public T set(int index, T data) {
        if (index >= 0 && data != null) {
            Node<T> pre = this.headNode.next;
            int j = 0;
            while (pre != null && j < index) {
                pre = pre.next;
                j++;
            }

            if (pre != null) {
                T oldData = pre.data;
                //设置新值，直接替换节点的数据，指向不需要改变
                pre.data = data;
                return oldData;
            }
        }
        return null;
    }

    /**
     * 根据下标添加结点
     * 1.头部插入
     * 2.中间插入
     * 3.末尾插入
     *
     * @param index 该值从0开始,如传4就代表插入在第5个位置
     * @param data  需要添加的数据
     * @return 是否添加成功
     */
    @Override
    public boolean add(int index, T data) {
        if (data == null) {
            throw new NullPointerException("data can\'t be empty!");
        }
        if (index < 0) {
            throw new NullPointerException("index can\'t less than 0");
        }

        //无需区分位置操作,中间/头部/尾部插入
        int j = 0;
        Node<T> pre = this.headNode;
        while (pre.next != null && j < index) {
            pre = pre.next;
            j++;
        }

        //将新插入的结点的后继指针指向pre.next，pre就是index前一位的节点
        Node<T> q = new Node<>(data, pre.next);
        //更改指针指向
        pre.next = q;

        //如果是尾指针，就需要移动尾指针rear的指向
        if (pre == this.rear) {
            this.rear = q;
        }
        return true;
    }

    /**
     * 尾部插入
     *
     * @param data 数据
     * @return 是否插入成功
     */
    @Override
    public boolean add(T data) {
        if (data == null) {
            throw new NullPointerException("data can\'t be empty!");
        }
        this.rear.next = new Node<>(data);
        //更新末尾指针的指向
        this.rear = this.rear.next;
        return true;
    }

    /**
     * 根据索引删除结点
     *
     * @param index 索引位置
     * @return 删除的数据
     */
    @Override
    public T remove(int index) {
        T old = null;

        //包含了头删除或中间删除或尾部删除的情况
        if (index >= 0) {
            Node<T> pre = this.headNode;
            int j = 0;
            //查找需要删除位置的前一个结点
            while (pre.next != null && j < index) {
                pre = pre.next;
                j++;
            }

            //获取到要删除的结点
            Node<T> r = pre.next;

            if (r != null) {
                //获取旧值
                old = r.data;
                //如果恰好是尾部结点,则更新rear的指向
                if (r == this.rear) {
                    this.rear = pre;
                }
                //更改指针指向
                pre.next = r.next;
            }
        }
        return old;
    }

    /**
     * 根据data移除所有数据相同的结点
     *
     * @param data 数据
     * @return 是否移除成功
     */
    @Override
    public boolean removeAll(T data) {
        boolean isRemove = false;

        if (data != null) {
            //用于记录要删除结点的前一个结点
            Node<T> front = this.headNode;
            //当前遍历的结点
            Node<T> pre = front.next;
            //查找所有数据相同的结点并删除
            while (pre != null) {
                if (data.equals(pre.data)) {
                    //如果恰好是尾部结点,则更新rear的指向
                    if (data.equals(rear.data)) {
                        this.rear = front;
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
        }
        return isRemove;
    }

    /**
     * 清空链表
     */
    @Override
    public void clear() {
        this.headNode.next = null;
        this.rear = this.headNode;
    }

    /**
     * 判断是否包含某个值
     *
     * @param data 数据
     * @return 是否包含某个值
     */
    @Override
    public boolean contains(T data) {
        if (data != null) {
            Node<T> pre = this.headNode.next;
            while (pre != null) {
                if (data.equals(pre.data)) {
                    return true;
                }
                pre = pre.next;
            }
        }
        return false;
    }

    /**
     * 从末尾连接两个链表
     *
     * @param list 链表
     */
    public void concat(HeadSingleLinkedList<T> list) {
        if (this.headNode.next == null) {
            this.headNode.next = list.headNode.next;
        } else {
//            Node<T> pre = this.headNode.next;
//            while (pre.next != null) {
//                pre = pre.next;
//            }
//            pre.next = list.headNode.next;

            this.rear.next = list.headNode.next;
            //更新尾部指针指向
            this.rear = list.rear;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("当前带头尾节点的单链表数据为(");
        Node<T> pre = this.headNode.next;
        while (pre != null) {
            sb.append(pre.data);
            pre = pre.next;
            if (pre != null) {
                sb.append(", ");
            }
        }
        return sb.append(")").append("\n").toString();
    }

    public static void main(String[] args) {

        HeadSingleLinkedList<String> linkedList = new HeadSingleLinkedList<>();
        linkedList.add("1111");
        linkedList.add("2222");
        linkedList.add("3333");
        System.out.println(linkedList.toString());
        HeadSingleLinkedList<String> list1 = new HeadSingleLinkedList<>(linkedList);
        System.out.println(list1.toString());


        String[] letters = {"A", "B", "C", "D", "E", "F"};
        HeadSingleLinkedList<String> list = new HeadSingleLinkedList<>(letters);


        System.out.println("list.get(3)->" + list.get(3));
        System.out.println("list:" + list.toString());

        System.out.println("list.add(4,Y)—>" + list.add(4, "Y"));
        System.out.println("list:" + list.toString());
        System.out.println("list.add(Z)—>" + list.add("Z"));
        System.out.println("list:" + list.toString());


        System.out.println("list.contains(Z)->" + list.contains("Z"));
        System.out.println("list.set(4,P)-->" + list.set(4, "P"));
        System.out.println("list:" + list.toString());

        System.out.println("list.remove(Z)->" + list.removeAll("Z"));
        System.out.println("list.remove(4)-->" + list.remove(4));
        System.out.println("list:" + list.toString());
    }
}
