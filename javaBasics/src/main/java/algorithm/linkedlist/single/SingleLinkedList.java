package algorithm.linkedlist.single;


import algorithm.linkedlist.ILinkedList;

/**
 * 单链表的实现,不含独立头结点,不含尾部指针
 *
 * @author Tang
 */
public class SingleLinkedList<T> implements ILinkedList<T> {
    /**
     * 带数据头结点
     */
    private Node<T> head;

    public SingleLinkedList(Node<T> head) {
        this.head = head;
    }

    public SingleLinkedList() {

    }

    /**
     * 判断链表是否为空
     *
     * @return 是否为空
     */
    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * 获取链表长度
     *
     * @return 返回链表的长度
     */
    @Override
    public int length() {
        int length = 0;
        Node<T> p = head;
        while (p != null) {
            length++;
            p = p.next;
        }
        return length;
    }


    /**
     * 根据index索引获取值
     *
     * @param index 下标值起始值为0
     * @return 查找的节点值
     */
    @Override
    public T get(int index) {
        if (this.head != null && index >= 0) {
            int count = 0;
            Node<T> p = this.head;
            //找到对应索引的结点
            while (p != null && count < index) {
                p = p.next;
                count++;
            }
            if (p != null) {
                return p.data;
            }
        }
        return null;
    }


    /**
     * 传入一个数组,转换成链表
     *
     * @param array 数组
     */
    public SingleLinkedList(T[] array) {
        this.head = null;
        if (array != null && array.length > 0) {
            this.head = new Node<>(array[0]);
            Node<T> rear = this.head;
            int i = 1;
            while (i < array.length) {
                // 创建一个节点，并且尾节点next指向当前创建的节点
                rear.next = new Node<>(array[i++]);
                // 移动尾节点
                rear = rear.next;
            }
        }
    }

    /**
     * 通过传入的链表构造新的链表
     *
     * @param list 链表
     */
    public SingleLinkedList(SingleLinkedList<T> list) {
        this.head = null;
        if (list != null && list.head != null) {
            this.head = new Node<>(list.head.data);
            // 引入了一个下一个元素的指针p，用于判断参数链表是否还有数据
            Node<T> p = list.head.next;
            Node<T> rear = this.head;
            while (p != null) {
                rear.next = new Node<>(p.data);
                rear = rear.next;
                p = p.next;
            }
        }
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
        if (this.head != null && index >= 0 && data != null) {
            Node<T> pre = this.head;
            int count = 0;
            while (pre != null && count < index) {
                pre = pre.next;
                count++;
            }

            if (pre != null) {
                T oldData = pre.data;
                // 设置新值
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
     * @param index 下标值从0开始
     * @param data  需要插入的数据
     * @return 是否添加成功
     */
    @Override
    public boolean add(int index, T data) {
        if (data == null) {
            return false;
        }
        // 在头部插入
        if (this.head == null || index <= 1) {
            // 创建一个新节点，并且后继节点为head
            this.head = new Node<>(data, this.head);
        } else {
            // 在尾部或中间插入，使用count来控制循环次数
            int count = 0;
            Node<T> front = this.head;
            while (front.next != null && count < index - 1) {
                front = front.next;
                count++;
            }
            // 尾部添加和中间插入属于同种情况,毕竟当front为尾部结点时front.next=null
            // 1.创建一个节点，并且该节点的下一个节点是front.next
            // 2.将front.next指向该节点
            front.next = new Node<>(data, front.next);
        }
        return true;
    }

    /**
     * 默认尾部插入
     *
     * @param data 数据
     * @return 是否添加成功
     */
    @Override
    public boolean add(T data) {
        return add(Integer.MAX_VALUE, data);
    }

    /**
     * 根据索引删除结点
     *
     * @param index 需要删除节点的位置
     * @return 当前删除的节点
     */
    @Override
    public T remove(int index) {
        T old = null;
        if (this.head != null && index >= 0) {
            // 直接删除的是头结点
            if (index == 0) {
                old = this.head.data;
                this.head = this.head.next;
            } else {
                Node<T> front = this.head;
                int count = 0;
                // 查找需要删除结点的【前一个结点】
                while (front.next != null && count < index - 1) {
                    front = front.next;
                    count++;
                }

                if (front.next != null) {
                    //获取旧值
                    old = front.next.data;
                    //更改指针指向
                    front.next = front.next.next;
                }
            }
        }
        return old;
    }

    /**
     * 根据data移除所有数据相同的结点
     *
     * @param data 需要删除的数据
     * @return 是否删除成功
     */
    @Override
    public boolean removeAll(T data) {
        boolean isRemove = false;
        if (this.head != null && data != null) {
            //如果移除的是头结点
            if (data.equals(this.head.data)) {
                this.head = this.head.next;
                // TODO: 2019/8/12 删除头结点，和头结点相同的数据没有删除
                isRemove = true;
            } else {
                Node<T> front = this.head;
                Node<T> pre = front.next;
                //查找所有数据相同的结点并删除
                while (pre != null) {
                    if (data.equals(pre.data)) {
                        //更改指针指向
                        front.next = pre.next;
                        pre = front.next;
//                        pre = pre.next;
                        isRemove = true;
                    } else {
                        front = pre;
                        pre = pre.next;
                    }
                }
            }
        }
        return isRemove;
    }

    /**
     * 根据data移除所有数据相同的结点
     *
     * @param data 需要删除的数据
     * @return 是否删除成功
     */
    public boolean removeAll1(T data) {
        boolean isRemove = false;
        if (this.head != null && data != null) {
            //如果移除的是头结点
            Node<T> front = this.head;
            Node<T> pre = front.next;
            //查找所有数据相同的结点并删除
            while (pre != null) {
                if (data.equals(pre.data)) {
                    //更改指针指向
                    front.next = pre.next;
                    pre = pre.next;
                    isRemove = true;
                } else {
                    front = pre;
                    pre = pre.next;
                }
            }
            // 判断头结点是否需要删除
            if (data.equals(this.head.data)) {
                if (this.head.next == null) {
                    this.head = null;
                } else {
                    this.head = this.head.next;
                }
                isRemove = true;
            }
        }
        return isRemove;
    }


    /**
     * 清空链表
     */
    @Override
    public void clear() {
        this.head = null;
    }

    /**
     * 判断是否包含某个值
     *
     * @param data 数据
     * @return 是否包含某个值
     */
    @Override
    public boolean contains(T data) {
        if (this.head != null && data != null) {
            Node<T> pre = this.head;
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
    public void concat(SingleLinkedList<T> list) {
        if (this.head == null) {
            this.head = list.head;
        } else {
            Node<T> pre = this.head;
            while (pre.next != null) {
                pre = pre.next;
            }
            pre.next = list.head;
        }
    }

    /**
     * 打印链表
     *
     * @return 链表字符串
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("当前单链表的数据为(");
        Node<T> pre = this.head;
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
        String[] letters = {"A", "B", "C", "D", "E", "F"};
        SingleLinkedList<String> list = new SingleLinkedList<>(letters);


        System.out.println("list.get(3)->" + list.get(3));
        System.out.println("list:" + list.toString());


        System.out.println("list.add(4,Y)—>" + list.add(4, "Y"));
        System.out.println("list.add(Z)—>" + list.add("Z"));
        System.out.println("list:" + list.toString());


        System.out.println("list.contains(Z)->" + list.contains("Z"));
        System.out.println("list.set(4,P)-->" + list.set(4, "P"));
        System.out.println("list:" + list.toString());


        System.out.println("list.removeAll(Z)->" + list.removeAll("Z"));
        System.out.println("list.remove(4)-->" + list.remove(4));
        System.out.println("list:" + list.toString());


        System.out.println("========================");
        String[] letters1 = {"A", "A", "B", "A", "A", "A"};
        SingleLinkedList<String> list1 = new SingleLinkedList<>(letters1);
        System.out.println(list1.removeAll1("A"));
        System.out.println(list1.removeAll1("B"));
        System.out.println("list:" + list1.toString());

    }

}
