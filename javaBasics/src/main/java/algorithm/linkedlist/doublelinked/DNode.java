package algorithm.linkedlist.doublelinked;

/**
 * 双链表结点
 */
public class DNode<T> {

    /**
     * 链表数据
     */
    public T data;

    /**
     * 前继指针和后继指针
     */
    DNode<T> prev, next;

    DNode(T data, DNode<T> prev, DNode<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public DNode(T data) {
        this(data, null, null);
    }

    public DNode() {
        this(null, null, null);
    }

    @Override
    public String toString() {
        return this.data.toString();
    }


}
